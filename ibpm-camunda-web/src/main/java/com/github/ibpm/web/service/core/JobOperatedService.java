package com.github.ibpm.web.service.core;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.enums.JobStatus;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.core.job.*;
import com.github.ibpm.common.result.core.job.Job;
import com.github.ibpm.common.result.core.job.JobExportAndImportResult;
import com.github.ibpm.common.result.core.job.JobWithVersionResult;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.JobMapper;
import com.github.ibpm.core.service.core.JobService;
import com.github.ibpm.core.util.FileUtil;
import com.github.ibpm.engine.model.BpmnResource;
import com.github.ibpm.engine.service.IbpmEngineService;
import com.github.ibpm.engine.util.EngineUtils;
import com.github.ibpm.engine.util.IoUtils;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import com.github.ibpm.sys.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private IbpmEngineService ibpmEngineService;

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
            job.setContent(ibpmEngineService.createSimpleTemplate(job.getJobName(), job.getDisplayName()));
        } else {
            job.setContent(EngineUtils.toBpmnXmlContent(
                    param.getJobName(), param.getDisplayName(), param.getContent()));
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
            return ibpmEngineService.getXmlContent(param.getProcDefId());
        }
    }

    public Void updateContent(JobContentSaveParam param) {
        Job job = validateAndGet(param.getJobName());
        if (StringUtils.isNotBlank(job.getContent())) {
            if (StringUtils.equals(param.getContent(), job.getContent())) {
                throw new RTException(6056);
            }
        } else {
            ibpmEngineService.validateDeployed(param.getJobName(), param.getContent());
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
     * publish a job to engine
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
            long deploymentTime = ibpmEngineService.deploy(
                    job.getJobName(), job.getDisplayName(), job.getContent());
            job.setUpdateTime(deploymentTime).setContent(null);
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
            content = ibpmEngineService.getXmlContent(param.getProcDefId());
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
        JobContentSaveParam saveParam = new JobContentSaveParam()
                .setContent(ibpmEngineService.getXmlContent(param.getProcDefId()));
        saveParam.setJobName(oldReversionJob.getJobName());
        return updateContent(saveParam);
    }

    /**
     * manual run jobs
     *
     * @param param
     * @return
     */
    public Void manualBatch(JobNamesParam param) {
        List<String> jobNames = param.getJobNames();
        ExecutorService executorService = Executors.newFixedThreadPool(jobNames.size());
        for (String jobName : jobNames) {
            executorService.submit(() -> {
                manual(new JobNameParam().setJobName(jobName));
            });
        }
        executorService.shutdown();
        return null;
    }

    /**
     * manual run job with param
     *
     * @param param
     * @return
     */
    public Void manual(JobNameParam param) {
        ibpmEngineService.createProcessInstance(param.getJobName());
        return null;
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
        for (String jobName : param.getJobNames()) {
            Job job = jobService.get(jobName);
            job.setUpdateTime(null) // import will init it
                .setContent(null); // reject temporary design
            JobExportAndImportResult mainModel = new JobExportAndImportResult();
            mainModel.setJob(job);
            jobModels.add(mainModel);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + param.getFileName());
        response.setContentType("application/zip");
        try (ServletOutputStream outputStream = response.getOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);) {
            if (jobModels.size() > 0) {
                String jobModelStr = BeanUtil.bean2JsonStr(jobModels);
                createZipEntry(zipOutputStream, JSON_FILE_JOB, IoUtils.stringAsInputStream(jobModelStr));
            }
            List<BpmnResource> bpmnResources = ibpmEngineService.getBpmnResources(param.getJobNames());
            for (BpmnResource bpmnResource : bpmnResources) {
                createZipEntry(zipOutputStream, bpmnResource.getResourceName(), bpmnResource.getInputStream());
            }
        } catch (IOException e) {
            throw new RTException(1100, e);
        }
    }

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
                                    job.setUpdateTime(ibpmEngineService.deploy(
                                            job.getJobName(), job.getDisplayName(), job.getContent()))
                                            .setContent(null);
                                } else {
                                    job.setUpdateTime(System.currentTimeMillis());
                                }
                                mapper.add(job);
                            } else { // update
                                if (nameDataMap.containsKey(job.getJobName() + SUFFIX_BPMN_XML)) { // need publish
                                    String content = nameDataMap.get(job.getJobName() + SUFFIX_BPMN_XML);
                                    job.setContent(content);
                                    job.setUpdateTime(ibpmEngineService.deploy(
                                            job.getJobName(), job.getDisplayName(), job.getContent()))
                                            .setContent(null);
                                } else {
                                    job.setUpdateTime(System.currentTimeMillis()).setContent(jobInDb.getContent());
                                }
                                mapper.update(job);
                            }
                        }
                    }
                }
            }
        }
        return null;
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
        zipOutputStream.write(IoUtils.inputStreamAsByteArray(is));
        zipOutputStream.closeEntry();
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

    private static final String SUFFIX_BPMN_XML = ".bpmn20.xml";
}
