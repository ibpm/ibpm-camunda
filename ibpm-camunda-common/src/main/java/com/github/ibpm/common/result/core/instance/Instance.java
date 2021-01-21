package com.github.ibpm.common.result.core.instance;

import lombok.ToString;

import java.util.Date;

@ToString
public class Instance {

    protected String processInstanceId;

    protected String rootProcessInstanceId;

    protected String superProcessInstanceId;


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

    protected String processDefinitionKey;

    protected String processDefinitionName;

    protected String processDefinitionId;

    protected Long duration;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public Instance setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getRootProcessInstanceId() {
        return rootProcessInstanceId;
    }

    public Instance setRootProcessInstanceId(String rootProcessInstanceId) {
        this.rootProcessInstanceId = rootProcessInstanceId;
        return this;
    }

    public String getSuperProcessInstanceId() {
        return superProcessInstanceId;
    }

    public Instance setSuperProcessInstanceId(String superProcessInstanceId) {
        this.superProcessInstanceId = superProcessInstanceId;
        return this;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public Instance setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public Instance setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
        return this;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public Instance setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
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
