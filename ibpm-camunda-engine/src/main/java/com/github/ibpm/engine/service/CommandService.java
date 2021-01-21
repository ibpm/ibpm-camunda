package com.github.ibpm.engine.service;

import com.github.ibpm.engine.cmd.impl.AddDraftCmd;
import com.github.ibpm.engine.cmd.impl.DeleteDraftCmd;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class CommandService {

    @Autowired
    private RuntimeService runtimeService;

    private ServiceImpl getServiceImpl() {
        return (ServiceImpl) runtimeService;
    }

    public Void addDraft(String processInstanceId, String businessKey, String processDefinitionKey, String processDefinitionId, Date startTime, String startUserId, String taskId) {
        return getServiceImpl().getCommandExecutor().execute(new AddDraftCmd(processInstanceId, businessKey, processDefinitionKey, processDefinitionId, startTime, startUserId, taskId));
    }

    public Void deleteDraft(String processInstanceId) {
        return getServiceImpl().getCommandExecutor().execute(new DeleteDraftCmd(processInstanceId));
    }
}
