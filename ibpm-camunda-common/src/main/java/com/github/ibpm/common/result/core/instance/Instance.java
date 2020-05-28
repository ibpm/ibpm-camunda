package com.github.ibpm.common.result.core.instance;

import lombok.ToString;

@ToString
public class Instance {

    protected String id;

    protected String procInstId;

    protected String rootProcInstId;

    protected String superProcInstId;

    protected String jobName;

    protected String displayName;

    protected String triggerName;

    protected String procDefId;

    protected Long startTime;

    protected Long endTime;

    protected Long duration;

    protected Integer status;

    protected Integer code;

    protected String msg;

    /**
     * when you retried, the field point to the parent id
     */
    protected String parentId;

    protected String schedulerUri;

    protected String executorUri;

    public String getId() {
        return id;
    }

    public Instance setId(String id) {
        this.id = id;
        return this;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public Instance setProcInstId(String procInstId) {
        this.procInstId = procInstId;
        return this;
    }

    public String getRootProcInstId() {
        return rootProcInstId;
    }

    public Instance setRootProcInstId(String rootProcInstId) {
        this.rootProcInstId = rootProcInstId;
        return this;
    }

    public String getSuperProcInstId() {
        return superProcInstId;
    }

    public Instance setSuperProcInstId(String superProcInstId) {
        this.superProcInstId = superProcInstId;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Instance setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public Instance setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Instance setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public Instance setTriggerName(String triggerName) {
        this.triggerName = triggerName;
        return this;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public Instance setProcDefId(String procDefId) {
        this.procDefId = procDefId;
        return this;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Instance setStartTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Instance setEndTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public Instance setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Instance setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Instance setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Instance setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getSchedulerUri() {
        return schedulerUri;
    }

    public Instance setSchedulerUri(String schedulerUri) {
        this.schedulerUri = schedulerUri;
        return this;
    }

    public String getExecutorUri() {
        return executorUri;
    }

    public Instance setExecutorUri(String executorUri) {
        this.executorUri = executorUri;
        return this;
    }
}
