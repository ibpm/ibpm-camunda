package com.github.ibpm.engine.model;

import lombok.Getter;

@Getter
public class ProcessData {

    protected String procInstId;

    protected String taskId;

    protected String jobName;

    protected String businessKey;

    protected String title;

    protected Integer status;

    public ProcessData setProcInstId(String procInstId) {
        this.procInstId = procInstId;
        return this;
    }

    public ProcessData setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public ProcessData setJobName(String jobName) {
        this.jobName = jobName;
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
