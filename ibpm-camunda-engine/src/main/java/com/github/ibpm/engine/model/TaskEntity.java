package com.github.ibpm.engine.model;

import lombok.Getter;

@Getter
public class TaskEntity {

    private String processInstanceId;

    private String taskId;

    public TaskEntity setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public TaskEntity setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }
}
