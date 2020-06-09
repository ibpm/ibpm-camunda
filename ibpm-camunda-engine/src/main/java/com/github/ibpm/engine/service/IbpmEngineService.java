package com.github.ibpm.engine.service;

import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.engine.constant.EngineConstant;
import com.github.ibpm.engine.model.BpmnResource;
import com.github.ibpm.engine.util.EngineUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.commons.utils.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class IbpmEngineService {
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    public long deploy(String id, String name, String xmlContent) {
        Deployment deployment = repositoryService.createDeployment()
                .addModelInstance(id + EngineConstant.SUFFIX_BPMN_XML,
                        EngineUtils.toBpmnModelInstance(xmlContent))
                .name(name)
                .deploy();
        return deployment.getDeploymentTime().getTime();
    }

    public void createProcessInstance(String id) {
        List<ProcessDefinition> definitions = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(id)
                .latestVersion()
                .list();
        if (definitions.size() == 1) {
            ProcessDefinition processDefinition = definitions.get(0);
            ProcessInstantiationBuilder builder = runtimeService.createProcessInstanceById(processDefinition.getId());
            identityService.setAuthenticatedUserId("ibpm");
            builder.execute();
        } else if (definitions.isEmpty()) {
            log.error("process not exists:{}", id);
            throw new RTException(2002);
        } else {
            log.error("process more than 1:{}", id);
            throw new RTException(2003);
        }
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

    public String getXmlContent(String procDefId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(procDefId)
                .singleResult();
        if (definition == null) {
            throw new RTException(6059, procDefId);
        }
        InputStream is = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        return IoUtil.inputStreamAsString(is);
    }
    
    public void validateDeployed(String id, String xmlContent) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(id)
                .latestVersion()
                .singleResult();
        if (definition == null) {
            throw new RTException(6059, id);
        }
        InputStream is = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        String latestContent = IoUtil.inputStreamAsString(is);
        if (StringUtils.equals(xmlContent, latestContent)) {
            throw new RTException(6056);
        }
    }
    
    public List<BpmnResource> getBpmnResources(List<String> ids) {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKeysIn(ids.toArray(new String[]{}))
                .latestVersion() // export latest version
                .list();
        List<BpmnResource> bpmnResources = new ArrayList<>(definitions.size());
        for (ProcessDefinition definition : definitions) {
            InputStream is = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
            bpmnResources.add(new BpmnResource().setResourceName(definition.getResourceName()).setInputStream(is));
        }
        return bpmnResources;
    }

    public String createSimpleTemplate(String id, String name) {
        BpmnModelInstance modelInstance = Bpmn.createExecutableProcess(id)
                .name(name)
                .startEvent()
                .id("start")
                .done();
        return Bpmn.convertToString(modelInstance);
    }
}
