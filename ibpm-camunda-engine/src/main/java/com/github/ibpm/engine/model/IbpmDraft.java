package com.github.ibpm.engine.model;

import lombok.Getter;

@Getter
public class IbpmDraft {
    private String processInstanceId;

    private String businessKey;

    // = processDefinitionKey
    private String processDefinitionKey;

    private String title;

    private String displayName;

    private String processDefinitionId;

    private long startTime;

    private Long removalTime;

    private String startUserId;

    private String deleteReason;

    public IbpmDraft setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public IbpmDraft setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public IbpmDraft setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public IbpmDraft setTitle(String title) {
        this.title = title;
        return this;
    }

    public IbpmDraft setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public IbpmDraft setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public IbpmDraft setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public IbpmDraft setRemovalTime(Long removalTime) {
        this.removalTime = removalTime;
        return this;
    }

    public IbpmDraft setStartUserId(String startUserId) {
        this.startUserId = startUserId;
        return this;
    }

    public IbpmDraft setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
        return this;
    }
}
