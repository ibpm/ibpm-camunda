package com.github.ibpm.common.result.core.process;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProcessModel {

    protected String processDefinitionKey;

    protected String processDefinitionName;

    protected String content;

    protected int status;

    private Long updateTime;

    protected String remark;

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public ProcessModel setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public ProcessModel setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
        return this;
    }

    public ProcessModel setContent(String content) {
        this.content = content;
        return this;
    }

    public ProcessModel setStatus(int status) {
        this.status = status;
        return this;
    }

    public ProcessModel setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public ProcessModel setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
