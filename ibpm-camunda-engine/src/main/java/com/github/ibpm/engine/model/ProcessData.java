package com.github.ibpm.engine.model;

import lombok.Getter;

@Getter
public class ProcessData {

    protected String processInstanceId;

    protected String taskId;

    protected String processDefinitionKey;

    protected String businessKey;

    protected String title;

    protected Integer status;

    public ProcessData setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public ProcessData setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public ProcessData setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public ProcessData setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public ProcessData setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProcessData setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
