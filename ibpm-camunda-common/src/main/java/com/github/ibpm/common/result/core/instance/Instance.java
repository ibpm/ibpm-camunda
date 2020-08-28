package com.github.ibpm.common.result.core.instance;

import lombok.ToString;

import java.util.Date;

@ToString
public class Instance {

    protected String id;

    protected String procInstId;

    protected String rootProcInstId;

    protected String superProcInstId;


    protected String taskId;

    protected Integer version;

    protected String starter;

    protected String assignees;

    protected String assignNames;

    protected String granters;

    protected String grantNames;

    protected String actId;

    protected String actName;

    protected Date startTime;

    protected Date endTime;

    protected Date assignTime;

    protected String businessKey;

    protected String title;

    protected Integer status;

    protected String jobName;

    protected String displayName;

    protected String procDefId;

    protected Long duration;

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

    public String getProcDefId() {
        return procDefId;
    }

    public Instance setProcDefId(String procDefId) {
        this.procDefId = procDefId;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public Instance setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public Instance setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public Instance setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getStarter() {
        return starter;
    }

    public Instance setStarter(String starter) {
        this.starter = starter;
        return this;
    }

    public String getAssignees() {
        return assignees;
    }

    public Instance setAssignees(String assignees) {
        this.assignees = assignees;
        return this;
    }

    public String getAssignNames() {
        return assignNames;
    }

    public Instance setAssignNames(String assignNames) {
        this.assignNames = assignNames;
        return this;
    }

    public String getGranters() {
        return granters;
    }

    public Instance setGranters(String granters) {
        this.granters = granters;
        return this;
    }

    public String getGrantNames() {
        return grantNames;
    }

    public Instance setGrantNames(String grantNames) {
        this.grantNames = grantNames;
        return this;
    }

    public String getActId() {
        return actId;
    }

    public Instance setActId(String actId) {
        this.actId = actId;
        return this;
    }

    public String getActName() {
        return actName;
    }

    public Instance setActName(String actName) {
        this.actName = actName;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Instance setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Instance setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public Instance setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Instance setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Instance setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public Instance setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }
}
