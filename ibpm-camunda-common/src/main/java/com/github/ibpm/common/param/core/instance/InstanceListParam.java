package com.github.ibpm.common.param.core.instance;

import com.github.ibpm.common.param.PageSortParam;
import lombok.ToString;

import java.util.List;

@ToString
public class InstanceListParam extends PageSortParam {

    protected String processInstanceId;

    protected String processDefinitionKey;

    protected String displayName;

    protected List<Integer> status;

    protected Long lowerStartTime;

    protected Long upperStartTime;

    protected Long lowerEndTime;

    protected Long upperEndTime;

    protected Long lowerDuration;

    protected Long upperDuration;

    protected boolean listChildren;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public InstanceListParam setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public InstanceListParam setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public InstanceListParam setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public InstanceListParam setStatus(List<Integer> status) {
        this.status = status;
        return this;
    }

    public Long getLowerStartTime() {
        return lowerStartTime;
    }

    public InstanceListParam setLowerStartTime(Long lowerStartTime) {
        this.lowerStartTime = lowerStartTime;
        return this;
    }

    public Long getUpperStartTime() {
        return upperStartTime;
    }

    public InstanceListParam setUpperStartTime(Long upperStartTime) {
        this.upperStartTime = upperStartTime;
        return this;
    }

    public Long getLowerEndTime() {
        return lowerEndTime;
    }

    public InstanceListParam setLowerEndTime(Long lowerEndTime) {
        this.lowerEndTime = lowerEndTime;
        return this;
    }

    public Long getUpperEndTime() {
        return upperEndTime;
    }

    public InstanceListParam setUpperEndTime(Long upperEndTime) {
        this.upperEndTime = upperEndTime;
        return this;
    }

    public Long getLowerDuration() {
        return lowerDuration;
    }

    public InstanceListParam setLowerDuration(Long lowerDuration) {
        this.lowerDuration = lowerDuration;
        return this;
    }

    public Long getUpperDuration() {
        return upperDuration;
    }

    public InstanceListParam setUpperDuration(Long upperDuration) {
        this.upperDuration = upperDuration;
        return this;
    }

    public boolean isListChildren() {
        return listChildren;
    }

    public InstanceListParam setListChildren(boolean listChildren) {
        this.listChildren = listChildren;
        return this;
    }
}
