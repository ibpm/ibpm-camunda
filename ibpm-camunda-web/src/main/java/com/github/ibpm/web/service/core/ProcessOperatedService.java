package com.github.ibpm.web.service.core;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.enums.ProcessStatus;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.core.process.*;
import com.github.ibpm.common.result.core.process.ProcessModel;
import com.github.ibpm.common.result.core.process.ProcessExportAndImportResult;
import com.github.ibpm.common.result.core.process.ProcessWithVersionResult;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.ProcessMapper;
import com.github.ibpm.core.service.core.ProcessService;
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
import org.apache.commons.lang3.StringUtils;
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
public class ProcessOperatedService extends BaseServiceAdapter {

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessMapper mapper;

    @Autowired
    private IbpmEngineService ibpmEngineService;

    public Map<String, Object> list(ProcessListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<ProcessModel> list = (Page<ProcessModel>) mapper.list(paramMap);
        return PageUtil.toResultMap(list);
    }

    public ProcessModel add(ProcessSaveParam param) {
        ProcessModel processModel = processService.get(new ProcessDefinitionKeyParam().setProcessDefinitionKey(param.getProcessDefinitionKey()));
        if (processModel != null) {
            throw new DuplicateKeyException(param.getProcessDefinitionKey());
        }
        processModel = toProcess(param);
        processModel.setUpdateTime(System.currentTimeMillis());
        if (StringUtils.isBlank(param.getContent())) {
            processModel.setContent(ibpmEngineService.createSimpleTemplate(processModel.getProcessDefinitionKey(), processModel.getProcessDefinitionName()));
        } else {
            processModel.setContent(EngineUtils.toBpmnXmlContent(
                    param.getProcessDefinitionKey(), param.getProcessDefinitionName(), param.getContent()));
        }
        mapper.add(processModel);
        return processModel;
    }

    public ProcessModel update(ProcessSaveParam param) {
        ProcessModel processModel = processService.get(new ProcessDefinitionKeyParam().setProcessDefinitionKey(param.getProcessDefinitionKey()));
        if (processModel == null) {
            return add(param); // Equivalent to copy then add
        }
        if (!StringUtils.equals(param.getProcessDefinitionName(), processModel.getProcessDefinitionName())) {
            throw new RTException(6080);
        }
        ProcessModel updatedProcessModel = toProcess(param);
        updatedProcessModel.setUpdateTime(System.currentTimeMillis());
        mapper.update(updatedProcessModel);
        return updatedProcessModel;
    }

    public String getContent(ProcessDefinitionKeyWithDefinitionParam param) {
        if (StringUtils.isBlank(param.getProcessDefinitionId())) {
            ProcessModel processModel = validateAndGet(param.getProcessDefinitionKey());
            return processModel.getContent();
        } else {
            return ibpmEngineService.getXmlContent(param.getProcessDefinitionId());
        }
    }

    public Void updateContent(ProcessContentSaveParam param) {
        ProcessModel processModel = validateAndGet(param.getProcessDefinitionKey());
        if (StringUtils.isNotBlank(processModel.getContent())) {
            if (StringUtils.equals(param.getContent(), processModel.getContent())) {
                throw new RTException(6056);
            }
        } else {
            ibpmEngineService.validateDeployed(param.getProcessDefinitionKey(), param.getContent());
        }
        processModel.setUpdateTime(System.currentTimeMillis());
        processModel.setContent(param.getContent());
        mapper.updateContent(processModel);
        return null;
    }

    public Void remove(ProcessDefinitionKeysParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.delete(paramMap);
        return null;
    }

    /**
     * publish a process to engine
     *
     * @param param
     * @return
     */
    public Void publish(ProcessDefinitionKeysParam param) {
        List<String> processDefinitionKeys = param.getProcessDefinitionKeys();
        for (String processDefinitionKey : processDefinitionKeys) {
            ProcessModel processModel = validateAndGet(processDefinitionKey);
            if (StringUtils.isBlank(processModel.getContent())) {
                throw new RTException(6053, processDefinitionKey);
            }
            long deploymentTime = ibpmEngineService.deploy(
                    processModel.getProcessDefinitionKey(), processModel.getProcessDefinitionName(), processModel.getContent());
            processModel.setUpdateTime(deploymentTime).setContent(null);
            mapper.updateContent(processModel);
        }
        return null;
    }

    private ProcessModel toProcess(ProcessSaveParam saveParam) {
        return new ProcessModel()
                .setProcessDefinitionKey(saveParam.getProcessDefinitionKey())
                .setProcessDefinitionName(saveParam.getProcessDefinitionName())
                .setContent(saveParam.getContent())
                .setStatus(saveParam.getStatus())
                .setRemark(saveParam.getRemark());
    }

    public ProcessModel validateAndGet(String processDefinitionKey) {
        ProcessDefinitionKeyParam processDefinitionKeyParam = new ProcessDefinitionKeyParam().setProcessDefinitionKey(processDefinitionKey);
        ProcessModel processModel = processService.get(processDefinitionKeyParam);
        if (processModel == null) {
            throw new RTException(6050, processDefinitionKey);
        }
        return processModel;
    }

    /**
     * copy process with current reversion to another process(if the target was existent, will add its reversion)
     *
     * @param param
     * @return
     */
    public ProcessModel copy(ProcessCopyParam param) {
        ProcessSaveParam targetProcessParam = param.getTargetProcessParam();
        ProcessModel sourceProcessModel = validateAndGet(param.getProcessDefinitionKey());
        ProcessModel targetProcessModel = processService.get(new ProcessDefinitionKeyParam().setProcessDefinitionKey(targetProcessParam.getProcessDefinitionKey()));
        String content;
        if (StringUtils.isBlank(param.getProcessDefinitionId())) {
            content = sourceProcessModel.getContent();
        } else {
            content = ibpmEngineService.getXmlContent(param.getProcessDefinitionId());
        }
        if (targetProcessModel != null) {
            ProcessSaveParam saveParam = toParam(targetProcessModel);
            saveParam.setContent(content);
            return update(saveParam);
        }
        targetProcessParam.setContent(content);
        return add(targetProcessParam);
    }

    /**
     * exchange current reversion to the latest
     *
     * @param param
     * @return
     */
    public Void exchange(ProcessDefinitionKeyWithDefinitionParam param) {
        ProcessModel oldReversionProcessModel = validateAndGet(param.getProcessDefinitionKey());
        if (StringUtils.isBlank(param.getProcessDefinitionId())) {
            return null;
        }
        ProcessContentSaveParam saveParam = new ProcessContentSaveParam()
                .setContent(ibpmEngineService.getXmlContent(param.getProcessDefinitionId()));
        saveParam.setProcessDefinitionKey(oldReversionProcessModel.getProcessDefinitionKey());
        return updateContent(saveParam);
    }

    /**
     * manual start process(es)
     *
     * @param param
     * @return
     */
    public String start(ProcessDefinitionKeysParam param) {
        List<String> processDefinitionKeys = param.getProcessDefinitionKeys();
        if (processDefinitionKeys.size() == 1) {
            String formKey = ibpmEngineService.getStartFormKeyByDefinitionKey(processDefinitionKeys.get(0));
            if (StringUtils.isBlank(formKey)) {
                ibpmEngineService.create(processDefinitionKeys.get(0), null, null);
                return null;
            } else {
                return formKey;
            }
        } else {
            ExecutorService executorService = Executors.newFixedThreadPool(processDefinitionKeys.size());
            for (String processDefinitionKey : processDefinitionKeys) {
                executorService.submit(() -> {
                    ibpmEngineService.create(processDefinitionKey, null, null);
                });
            }
            executorService.shutdown();
            return null;
        }
    }

    /**
     * list all versions by a specified process displayName
     *
     * @param param
     * @return
     */
    public List<ProcessWithVersionResult> versions(ProcessDefinitionKeyParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        List<ProcessWithVersionResult> processWithVersionResults = mapper.versions(paramMap);
        ProcessModel processModel = validateAndGet(param.getProcessDefinitionKey());
        if (StringUtils.isNotBlank(processModel.getContent())) {
            ProcessWithVersionResult result = new ProcessWithVersionResult()
                    .setDelpoyTime(new Date(processModel.getUpdateTime()));
            result
                    .setVersion(processWithVersionResults.isEmpty() ? 0 :
                        processWithVersionResults.get(processWithVersionResults.size() - 1).getVersion() + 1)
                    .setProcessDefinitionKey(processModel.getProcessDefinitionKey());
            processWithVersionResults.add(result);
        }
        return processWithVersionResults;
    }

    private static final String JSON_FILE_JOB = "process.json";

    /**
     * export process and(or) trigger and(or) arg and(or) project
     * @param response
     * @param param
     */
    public void exportModel(HttpServletResponse response, ProcessExportParam param) {
        List<ProcessExportAndImportResult> processModels = new ArrayList<>();
        for (String processDefinitionKey : param.getProcessDefinitionKeys()) {
            ProcessModel processModel = processService.get(processDefinitionKey);
            processModel.setUpdateTime(null) // import will init it
                .setContent(null); // reject temporary design
            ProcessExportAndImportResult mainModel = new ProcessExportAndImportResult();
            mainModel.setProcess(processModel);
            processModels.add(mainModel);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + param.getFileName());
        response.setContentType("application/zip");
        try (ServletOutputStream outputStream = response.getOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);) {
            if (processModels.size() > 0) {
                String processModelStr = BeanUtil.bean2JsonStr(processModels);
                createZipEntry(zipOutputStream, JSON_FILE_JOB, IoUtils.stringAsInputStream(processModelStr));
            }
            List<BpmnResource> bpmnResources = ibpmEngineService.getBpmnResources(param.getProcessDefinitionKeys());
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
            String processModelStr = nameDataMap.get(JSON_FILE_JOB);
            if (StringUtils.isNotBlank(processModelStr)) {
                List<Map<String, Object>> processModels = BeanUtil.jsonStr2Bean(processModelStr, List.class);
                if (processModels.size() > 0) {
                    for (Map<String, Object> map : processModels) {
                        ProcessExportAndImportResult exportedModel = BeanUtil.map2Bean(ProcessExportAndImportResult.class, map);
                        ProcessModel processModel = exportedModel.getProcess();
                        if (processModel != null) {
                            ProcessModel processModelInDb = processService.get(processModel.getProcessDefinitionKey());
                            if (processModelInDb == null) { // insert
                                if (nameDataMap.containsKey(processModel.getProcessDefinitionKey() + SUFFIX_BPMN_XML)) { // need publish
                                    String content = nameDataMap.get(processModel.getProcessDefinitionKey() + SUFFIX_BPMN_XML);
                                    processModel.setContent(content);
                                    processModel.setUpdateTime(ibpmEngineService.deploy(
                                            processModel.getProcessDefinitionKey(), processModel.getProcessDefinitionName(), processModel.getContent()))
                                            .setContent(null);
                                } else {
                                    processModel.setUpdateTime(System.currentTimeMillis());
                                }
                                mapper.add(processModel);
                            } else { // update
                                if (nameDataMap.containsKey(processModel.getProcessDefinitionKey() + SUFFIX_BPMN_XML)) { // need publish
                                    String content = nameDataMap.get(processModel.getProcessDefinitionKey() + SUFFIX_BPMN_XML);
                                    processModel.setContent(content);
                                    processModel.setUpdateTime(ibpmEngineService.deploy(
                                            processModel.getProcessDefinitionKey(), processModel.getProcessDefinitionName(), processModel.getContent()))
                                            .setContent(null);
                                } else {
                                    processModel.setUpdateTime(System.currentTimeMillis()).setContent(processModelInDb.getContent());
                                }
                                mapper.update(processModel);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private ProcessSaveParam toParam(ProcessModel processModel) {
        ProcessSaveParam param = new ProcessSaveParam()
                .setProcessDefinitionName(processModel.getProcessDefinitionName()).setStatus(processModel.getStatus())
                .setRemark(processModel.getRemark());
        param.setProcessDefinitionKey(processModel.getProcessDefinitionKey());
        return param;
    }

    private void createZipEntry(ZipOutputStream zipOutputStream, String fileName, InputStream is) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(IoUtils.inputStreamAsByteArray(is));
        zipOutputStream.closeEntry();
    }

    public Void enable(ProcessDefinitionKeysParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.status, ProcessStatus.ENABLED.getStatus());
        mapper.updateStatus(paramMap);
        return null;
    }

    public Void disable(ProcessDefinitionKeysParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.status, ProcessStatus.DISABLED.getStatus());
        mapper.updateStatus(paramMap);
        return null;
    }

    private static final String SUFFIX_BPMN_XML = ".bpmn20.xml";
}
