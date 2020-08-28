package com.github.ibpm.engine.model;

import lombok.Getter;

@Getter
public class IbpmDraft {
    private String procInstId;

    private String businessKey;

    // = jobName
    private String jobName;

    private String title;

    private String displayName;

    private String procDefId;

    private long startTime;

    private Long removalTime;

    private String startUserId;

    private String deleteReason;

    public IbpmDraft setProcInstId(String procInstId) {
        this.procInstId = procInstId;
        return this;
    }

    public IbpmDraft setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public IbpmDraft setJobName(String jobName) {
        this.jobName = jobName;
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

    public IbpmDraft setProcDefId(String procDefId) {
        this.procDefId = procDefId;
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
