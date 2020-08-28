package com.github.ibpm.engine.service;

import com.github.ibpm.common.enums.InstanceStatus;
import com.github.ibpm.config.bean.SpringContextAware;
import com.github.ibpm.config.id.MyIdGenerator;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.engine.model.ProcessData;
import com.github.ibpm.engine.model.TaskEntity;
import com.github.ibpm.engine.service.instance.InstanceService;
import com.github.ibpm.sys.holder.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class BpmService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MyIdGenerator myIdGenerator;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private IbpmEngineService ibpmEngineService;

    @Autowired
    private CommandService commandService;

    public Void draft(Map<String, Object> paramMap) {
        ProcessData processData = save(paramMap);
        processData.setStatus(InstanceStatus.DRAFT.getStatus());
        String procInstId = processData.getProcInstId();
        if (StringUtils.isBlank(procInstId)) {
            processData.setProcInstId(idGenerator.getNextId());
            instanceService.add(processData);
            ProcessDefinition processDefinition = ibpmEngineService.getLatestProcessDefinitionByKey(processData.getJobName());
            commandService.addDraft(processData.getProcInstId(), processData.getBusinessKey(), processData.getJobName(), processDefinition.getId(),
                    new Date(), UserHolder.get().getUserName(), idGenerator.getNextId());
        } else {
            instanceService.update(processData);
        }
        return null;
    }

    public Void create(Map<String, Object> paramMap) {
        ProcessData processData = save(paramMap);
        Integer status = processData.getStatus();
        if (status != null && status == InstanceStatus.DRAFT.getStatus()) {
            instanceService.deleteExecution(processData.getProcInstId());
            instanceService.deleteInstance(processData.getProcInstId());
            commandService.deleteDraft(processData.getProcInstId());
        }
        ProcessInstance processInstance = ibpmEngineService.create(processData.getJobName(), processData.getBusinessKey(), paramMap);
        processData.setProcInstId(processInstance.getProcessInstanceId())
                .setStatus(InstanceStatus.RUNNING.getStatus());
        instanceService.add(processData);
        ibpmEngineService.approve(processInstance.getProcessInstanceId(), paramMap);
        return null;
    }

    public Void approve(Map<String, Object> paramMap) {
        ProcessData processData = save(paramMap);
        instanceService.update(processData);
        ibpmEngineService.approve(new TaskEntity().setProcessInstanceId(processData.getProcInstId()).setTaskId(processData.getTaskId()), paramMap);
        return null;
    }

    public Object get(Map<String, Object> paramMap) {
        ProcessData processData = BeanUtil.map2Bean(ProcessData.class, paramMap);
        String processDefinitionKey = ibpmEngineService.getProcessDefinitionKeyByBusinessKey(processData.getBusinessKey());
        BizService bizService = (BizService) SpringContextAware.getBean(processDefinitionKey);
        return bizService.get(paramMap);
    }

    public ProcessData save(Map<String, Object> paramMap) {
        ProcessData processData = BeanUtil.map2Bean(ProcessData.class, paramMap);
        BizService bizService = (BizService) SpringContextAware.getBean(processData.getJobName());
        String businessKey = processData.getBusinessKey();
        if (StringUtils.isBlank(businessKey)) {
            businessKey = myIdGenerator.getNextId();
            processData.setBusinessKey(businessKey);
            paramMap.put(BUSINESS_KEY, businessKey);
            bizService.add(paramMap);
        } else {
            bizService.update(paramMap);
        }
        return processData;
    }

    private static final String BUSINESS_KEY = "businessKey";
}
