package com.github.ibpm.web.service.core;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.enums.JobStatus;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.core.arg.ArgNamesParam;
import com.github.ibpm.common.param.core.job.*;
import com.github.ibpm.common.result.core.arg.Arg;
import com.github.ibpm.common.result.core.job.Job;
import com.github.ibpm.common.result.core.job.JobExportAndImportResult;
import com.github.ibpm.common.result.core.job.JobWithVersionResult;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.JobMapper;
import com.github.ibpm.core.service.core.ArgService;
import com.github.ibpm.core.service.core.JobService;
import com.github.ibpm.core.util.FileUtil;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import com.github.ibpm.sys.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.commons.utils.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@Transactional
public class JobOperatedService extends BaseServiceAdapter {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapper mapper;

    @Autowired
    private RepositoryService repositoryService;

    public Map<String, Object> list(JobListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<Job> list = (Page<Job>) mapper.list(paramMap);
        return PageUtil.toResultMap(list);
    }

    public Job add(JobSaveParam param) {
        Job job = jobService.get(new JobNameParam().setJobName(param.getJobName()));
        if (job != null) {
            throw new DuplicateKeyException(param.getJobName());
        }
        job = toJob(param);
        job.setUpdateTime(System.currentTimeMillis());
        if (StringUtils.isBlank(param.getContent())) {
            BpmnModelInstance modelInstance = Bpmn.createExecutableProcess(job.getJobName())
                    .name(job.getDisplayName())
                    .startEvent()
                    .id("start")
                    .done();
            job.setContent(Bpmn.convertToString(modelInstance));
        } else {
            BpmnModelInstance bpmnModelInstance = toBpmnModelInstance(param.getContent());
            Collection<Process> modelElements = bpmnModelInstance.getModelElementsByType(Process.class);
            for (Process process : modelElements) {
                process.builder().id(param.getJobName()).name(param.getDisplayName());
                break;
            }
            job.setContent(Bpmn.convertToString(bpmnModelInstance));
        }
        mapper.add(job);
        return job;
    }

    public Job update(JobSaveParam param) {
        Job job = jobService.get(new JobNameParam().setJobName(param.getJobName()));
        if (job == null) {
            return add(param); // Equivalent to copy then add
        }
        if (!StringUtils.equals(param.getDisplayName(), job.getDisplayName())) {
            throw new RTException(6080);
        }
        Job updatedJob = toJob(param);
        updatedJob.setUpdateTime(System.currentTimeMillis());
        mapper.update(updatedJob);
        return updatedJob;
    }

    public String getContent(JobNameWithDefinitionParam param) {
        if (StringUtils.isBlank(param.getProcDefId())) {
            Job job = validateAndGet(param.getJobName());
            return job.getContent();
        } else {
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(param.getProcDefId())
                    .singleResult();
            InputStream is = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
            return IoUtil.inputStreamAsString(is);
        }
    }

    public Void updateContent(JobContentSaveParam param) {
        Job job = validateAndGet(param.getJobName());
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(param.getJobName())
                .latestVersion()
                .singleResult();
        if (StringUtils.isNotBlank(job.getContent())) {
            if (StringUtils.equals(param.getContent(), job.getContent())) {
                throw new RTException(6056);
            }
        } else {
            if (definition != null) {
                InputStream is = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
                String latestContent = IoUtil.inputStreamAsString(is);
                if (StringUtils.equals(param.getContent(), latestContent)) {
                    throw new RTException(6056);
                }
            }
        }
        job.setUpdateTime(System.currentTimeMillis());
        job.setContent(param.getContent());
        mapper.updateContent(job);
        return null;
    }

    public Void remove(JobNamesParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.delete(paramMap);
        return null;
    }

    /**
     * publish a job to camunda engine
     *
     * @param param
     * @return
     */
    public Void publish(JobNamesParam param) {
        List<String> jobNames = param.getJobNames();
        for (String jobName : jobNames) {
            Job job = validateAndGet(jobName);
            if (StringUtils.isBlank(job.getContent())) {
                throw new RTException(6053, jobName);
            }
            Deployment deployment = deploy(job);
            job.setUpdateTime(deployment.getDeploymentTime().getTime()).setContent(null);
            mapper.updateContent(job);
        }
        return null;
    }

    private Job toJob(JobSaveParam saveParam) {
        return new Job()
                .setJobName(saveParam.getJobName())
                .setDisplayName(saveParam.getDisplayName())
                .setContent(saveParam.getContent())
                .setStatus(saveParam.getStatus())
                .setRemark(saveParam.getRemark());
    }

    public Job validateAndGet(String jobName) {
        JobNameParam jobNameParam = new JobNameParam().setJobName(jobName);
        Job job = jobService.get(jobNameParam);
        if (job == null) {
            throw new RTException(6050, jobName);
        }
        return job;
    }

    /**
     * copy job with current reversion to another job(if the target was existent, will add its reversion)
     *
     * @param param
     * @return
     */
    public Job copy(JobCopyParam param) {
        JobSaveParam targetJobParam = param.getTargetJobParam();
        Job sourceJob = validateAndGet(param.getJobName());
        Job targetJob = jobService.get(new JobNameParam().setJobName(targetJobParam.getJobName()));
        String content;
        if (StringUtils.isBlank(param.getProcDefId())) {
            content = sourceJob.getContent();
        } else {
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionId(param.getProcDefId())
                    .singleResult();
            if (processDefinition == null) {
                throw new RTException(6059, param.getJobName());
            }
            InputStream is = repositoryService.getResourceAsStream(
                    processDefinition.getDeploymentId(), processDefinition.getResourceName());
            content = IoUtil.inputStreamAsString(is);
        }
        if (targetJob != null) {
            JobSaveParam saveParam = toParam(targetJob);
            saveParam.setContent(content);
            return update(saveParam);
        }
        targetJobParam.setContent(content);
        return add(targetJobParam);
    }

    /**
     * exchange current reversion to the latest
     *
     * @param param
     * @return
     */
    public Void exchange(JobNameWithDefinitionParam param) {
        Job oldReversionJob = validateAndGet(param.getJobName());
        if (StringUtils.isBlank(param.getProcDefId())) {
            return null;
        }
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(param.getProcDefId())
                .singleResult();
        if (processDefinition == null) {
            throw new RTException(6059, param.getJobName());
        }
        InputStream is = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(), processDefinition.getResourceName());
        JobContentSaveParam saveParam = new JobContentSaveParam()
                .setContent(IoUtil.inputStreamAsString(is));
        saveParam.setJobName(oldReversionJob.getJobName());
        return updateContent(saveParam);
    }

    /**
     * list all versions by a specified job displayName
     *
     * @param param
     * @return
     */
    public List<JobWithVersionResult> versions(JobNameParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        List<JobWithVersionResult> jobWithVersionResults = mapper.versions(paramMap);
        Job job = validateAndGet(param.getJobName());
        if (StringUtils.isNotBlank(job.getContent())) {
            JobWithVersionResult result = new JobWithVersionResult()
                    .setJobName(job.getJobName())
                    .setUpdateTime(new Date(job.getUpdateTime()))
                    .setVersion(jobWithVersionResults.isEmpty() ? 0 :
                            jobWithVersionResults.get(jobWithVersionResults.size() - 1).getVersion() + 1);
            jobWithVersionResults.add(result);
        }
        return jobWithVersionResults;
    }

    private static final String JSON_FILE_JOB = "job.json";

    private static final String JSON_FILE_ARG = "arg.json";

    /**
     * export job and(or) trigger and(or) arg and(or) project
     * @param response
     * @param param
     */
    public void exportModel(HttpServletResponse response, JobExportParam param) {
        List<JobExportAndImportResult> jobModels = new ArrayList<>();
        Set<Arg> argModels = new HashSet<>();
        for (String jobName : param.getJobNames()) {
            Job job = jobService.get(jobName);
            job.setUpdateTime(null) // import will init it
                .setContent(null); // reject temporary design
            JobExportAndImportResult mainModel = new JobExportAndImportResult();
            mainModel.setJob(job);
            List<Arg> args = jobService.getArg(jobName);
            if (args.size() > 0) {
                mainModel.setArgs(args.stream().map(Arg::getArgName).collect(Collectors.toList()));
                argModels.addAll(args);
            }
            jobModels.add(mainModel);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + param.getFileName());
        response.setContentType("application/zip");
        try (ServletOutputStream outputStream = response.getOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);) {
            if (jobModels.size() > 0) {
                String jobModelStr = BeanUtil.bean2JsonStr(jobModels);
                createZipEntry(zipOutputStream, JSON_FILE_JOB, IoUtil.stringAsInputStream(jobModelStr));
            }
            if (argModels.size() > 0) {
                String argModelStr = BeanUtil.bean2JsonStr(argModels);
                createZipEntry(zipOutputStream, JSON_FILE_ARG, IoUtil.stringAsInputStream(argModelStr));
            }
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKeysIn(param.getJobNames().toArray(new String[]{}))
                    .latestVersion() // export latest version
                    .list();
            for (ProcessDefinition definition : processDefinitions) {
                InputStream resourceAsStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
                createZipEntry(zipOutputStream, definition.getResourceName(), resourceAsStream);
            }
        } catch (IOException e) {
            throw new RTException(1100, e);
        }
    }

    @Autowired
    private ArgService argService;

    public Void importModel(MultipartFile file) {
        Map<String, String> nameDataMap = new HashMap<>();
        try (InputStream is = file.getInputStream();
             ZipInputStream zis = new ZipInputStream(is);){
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                nameDataMap.put(zipEntry.getName(), new String(FileUtil.inputStreamAsByteArray(zis), "UTF-8"));
            }
        } catch (IOException e) {
            throw new RTException(1100, e);
        }
        if (nameDataMap.containsKey(JSON_FILE_ARG)) {
            String argModelStr = nameDataMap.get(JSON_FILE_ARG);
            if (StringUtils.isNotBlank(argModelStr)) {
                List<Map<String, Object>> argModels = BeanUtil.jsonStr2Bean(argModelStr, List.class);
                if (argModels.size() > 0) {
                    List<Arg> args = new ArrayList<>(argModels.size());
                    List<String> argNames = new ArrayList<>(argModels.size());
                    for (Map<String, Object> map : argModels) {
                        Arg arg = BeanUtil.map2Bean(Arg.class, map);
                        argNames.add(arg.getArgName());
                        args.add(arg);
                    }
                    // 1.remove args
                    argService.remove(new ArgNamesParam().setArgNames(argNames));
                    // 2. insert args
                    argService.addBatch(args);
                }
            }
        }
        if (nameDataMap.containsKey(JSON_FILE_JOB)) {
            String jobModelStr = nameDataMap.get(JSON_FILE_JOB);
            if (StringUtils.isNotBlank(jobModelStr)) {
                List<Map<String, Object>> jobModels = BeanUtil.jsonStr2Bean(jobModelStr, List.class);
                if (jobModels.size() > 0) {
                    for (Map<String, Object> map : jobModels) {
                        JobExportAndImportResult exportedModel = BeanUtil.map2Bean(JobExportAndImportResult.class, map);
                        Job job = exportedModel.getJob();
                        if (job != null) {
                            Job jobInDb = jobService.get(job.getJobName());
                            if (jobInDb == null) { // insert
                                if (nameDataMap.containsKey(job.getJobName() + SUFFIX_BPMN_XML)) { // need publish
                                    String content = nameDataMap.get(job.getJobName() + SUFFIX_BPMN_XML);
                                    job.setContent(content);
                                    Deployment deployment = deploy(job);
                                    job.setUpdateTime(deployment.getDeploymentTime().getTime()).setContent(null);
                                } else {
                                    job.setUpdateTime(System.currentTimeMillis());
                                }
                                mapper.add(job);
                            } else { // update
                                if (nameDataMap.containsKey(job.getJobName() + SUFFIX_BPMN_XML)) { // need publish
                                    String content = nameDataMap.get(job.getJobName() + SUFFIX_BPMN_XML);
                                    job.setContent(content);
                                    Deployment deployment = deploy(job);
                                    job.setUpdateTime(deployment.getDeploymentTime().getTime()).setContent(null);
                                } else {
                                    job.setUpdateTime(System.currentTimeMillis()).setContent(jobInDb.getContent());
                                }
                                mapper.update(job);
                            }
                            List<String> argNames = exportedModel.getArgs();
                            if (argNames != null && argNames.size() > 0) {
                                saveJobArg(job.getJobName(), argNames);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * the job was published to camunda or not
     * @param jobName
     * @return
     */
    public void validatePublished(String jobName) {
        long count = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(jobName)
                .count();
        if (count <= 0){
            throw new RTException(6058, jobName);
        }
    }

    private JobSaveParam toParam(Job job) {
        JobSaveParam param = new JobSaveParam()
                .setDisplayName(job.getDisplayName()).setStatus(job.getStatus())
                .setRemark(job.getRemark());
        param.setJobName(job.getJobName());
        return param;
    }

    private void createZipEntry(ZipOutputStream zipOutputStream, String fileName, InputStream is) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(IoUtil.inputStreamAsByteArray(is));
        zipOutputStream.closeEntry();
    }

    public Void addArg(JobArgAllocatedParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.addArg(paramMap);
        return null;
    }

    public Void removeArg(JobArgAllocatedParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.deleteArg(paramMap);
        return null;
    }

    public void saveJobArg(String jobName, List<String> argNames) {
        JobArgSaveParam param = new JobArgSaveParam().setArgNames(argNames);
        param.setJobName(jobName);
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.deleteJobArg(paramMap);
        if (param.getArgNames() != null && param.getArgNames().size() > 0) {
            mapper.addJobArg(paramMap);
        }
    }

    private Deployment deploy(Job job) {
        return repositoryService.createDeployment()
                .addModelInstance(job.getJobName() + SUFFIX_BPMN_XML,
                        toBpmnModelInstance(job.getContent()))
                .name(job.getDisplayName())
                .deploy();
    }

    public Void enable(JobNamesParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.status, JobStatus.ENABLED.getStatus());
        mapper.updateStatus(paramMap);
        return null;
    }

    public Void disable(JobNamesParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.status, JobStatus.DISABLED.getStatus());
        mapper.updateStatus(paramMap);
        return null;
    }

    private BpmnModelInstance toBpmnModelInstance(String xmlContent) {
        try {
            return Bpmn.readModelFromStream(new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RTException(1100, e);
        }
    }

    private static final String SUFFIX_BPMN_XML = ".bpmn20.xml";
}
