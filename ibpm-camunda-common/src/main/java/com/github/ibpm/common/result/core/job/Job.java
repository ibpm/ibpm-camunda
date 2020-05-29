package com.github.ibpm.common.result.core.job;

import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
public class Job {

    protected String jobName;

    protected String displayName;

    protected String content;

    protected int status;

    protected Long updateTime;

    protected String remark;

    public String getJobName() {
        return jobName;
    }

    public Job setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Job setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Job setContent(String content) {
        this.content = content;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Job setStatus(int status) {
        this.status = status;
        return this;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public Job setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Job setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}