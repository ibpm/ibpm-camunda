package com.github.ibpm.engine.service;

import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.engine.constant.EngineConstant;
import com.github.ibpm.engine.model.BpmnResource;
import com.github.ibpm.engine.model.TaskEntity;
import com.github.ibpm.engine.util.EngineUtils;
import com.github.ibpm.sys.holder.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.commons.utils.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@Transactional
public class IbpmEngineService {
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private FormService formService;

    public long deploy(String id, String name, String xmlContent) {
        Deployment deployment = repositoryService.createDeployment()
                .addModelInstance(id + EngineConstant.SUFFIX_BPMN_XML,
                        EngineUtils.toBpmnModelInstance(xmlContent))
                .name(name)
                .deploy();
        return deployment.getDeploymentTime().getTime();
    }

    public String getStartFormKeyByDefinitionKey(String id) {
        ProcessDefinition definition = getLatestProcessDefinitionByKey(id);
        return getStartFormKeyByDefinition(definition);
    }

    public String getStartFormKeyByDefinitionId(String processDefinitionId) {
        ProcessDefinition definition = getProcessDefinitionById(processDefinitionId);
        return getStartFormKeyByDefinition(definition);
    }

    public String getStartFormKeyByDefinition(ProcessDefinition definition) {
        return formService.getStartFormKey(definition.getId());
    }

    public String getTaskFormKey(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).initializeFormKeys().singleResult();
        return StringUtils.isBlank(task.getFormKey()) ? getStartFormKeyByDefinitionId(task.getProcessDefinitionId()) : task.getFormKey();
    }

    /**
     * start a process by process id
     * @param id
     */
    public ProcessInstance create(String id, String businessKey, Map<String, Object> bizMap) {
        ProcessDefinition definition = getLatestProcessDefinitionByKey(id);
        ProcessInstantiationBuilder builder = runtimeService.createProcessInstanceById(definition.getId())
                .businessKey(businessKey);
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(definition.getId());
        Collection<StartEvent> startEvents = bpmnModelInstance.getModelElementsByType(StartEvent.class);
        for (StartEvent startEvent : startEvents) {
            String initiator = startEvent.getCamundaInitiator();
            if (StringUtils.isNotBlank(initiator)) {
                if (EngineUtils.isExpression(initiator)) {
                    String expression = EngineUtils.extractFromExpression(initiator);
                    builder.setVariable(expression, UserHolder.get().getUserName())
                        .setVariables(bizMap);
                    identityService.setAuthenticatedUserId(UserHolder.get().getUserName());
                } else {
                    identityService.setAuthenticatedUserId(initiator);
                }
            } else {
                identityService.setAuthenticatedUserId(UserHolder.get().getUserName());
            }
            break;
        }
        ProcessInstance processInstance = builder.execute();
        identityService.setAuthenticatedUserId(null);
        return processInstance;
    }

    public void approve(String processInstanceId, Map<String, Object> bizMap) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        Map<String, Object> variableMap = new HashMap<>();
        String assignee = task.getAssignee();
        if (StringUtils.isNotBlank(assignee) && EngineUtils.isExpression(assignee)) {
            String expression = EngineUtils.extractFromExpression(assignee);
            variableMap.put(expression, UserHolder.get().getUserName());
        }
        if (bizMap != null) {
            variableMap.putAll(bizMap);
        }
        taskService.complete(task.getId(), variableMap);
    }

    public void approve(TaskEntity taskEntity, Map<String, Object> bizMap) {
        String taskId = taskEntity.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String, Object> variableMap = new HashMap<>();
        String assignee = task.getAssignee();
        if (StringUtils.isNotBlank(assignee) && EngineUtils.isExpression(assignee)) {
            String expression = EngineUtils.extractFromExpression(assignee);
            variableMap.put(expression, UserHolder.get().getUserName());
        }
        if (bizMap != null) {
            variableMap.putAll(bizMap);
        }
        taskService.complete(task.getId(), variableMap);
    }

    public String getProcessDefinitionKeyByBusinessKey(String businessKey) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey).singleResult();
        if (historicProcessInstance == null) {
            throw new RTException("HistoricProcessInstance[businessKey=" + businessKey + "] is null");
        }
        return historicProcessInstance.getProcessDefinitionKey();
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

    public String getXmlContent(String processDefinitionId) {
        ProcessDefinition definition = getProcessDefinitionById(processDefinitionId);
        InputStream is = getResourceAsStream(definition);
        return IoUtil.inputStreamAsString(is);
    }
    
    public void validateDeployed(String id, String xmlContent) {
        ProcessDefinition definition = getLatestProcessDefinitionByKey(id);
        InputStream is = getResourceAsStream(definition);
        String latestContent = IoUtil.inputStreamAsString(is);
        if (StringUtils.equals(xmlContent, latestContent)) {
            throw new RTException(6056);
        }
    }

    public ProcessDefinition getLatestProcessDefinitionByKey(String id) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(id)
                .latestVersion()
                .singleResult();
        if (definition == null) {
            throw new RTException(6059, id);
        }
        return definition;
    }

    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        if (definition == null) {
            throw new RTException(6059, processDefinitionId);
        }
        return definition;
    }

    public List<BpmnResource> getBpmnResources(List<String> ids) {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKeysIn(ids.toArray(new String[]{}))
                .latestVersion() // export latest version
                .list();
        List<BpmnResource> bpmnResources = new ArrayList<>(definitions.size());
        for (ProcessDefinition definition : definitions) {
            InputStream is = getResourceAsStream(definition);
            bpmnResources.add(new BpmnResource().setResourceName(definition.getResourceName()).setInputStream(is));
        }
        return bpmnResources;
    }

    private InputStream getResourceAsStream(ProcessDefinition definition) {
        return repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
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
