package com.github.ibpm.common.result.core.process;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class ProcessWithVersionResult extends ProcessModel {

    protected Date deployTime;

    protected String processDefinitionId;

    protected int version;

    public ProcessWithVersionResult setDelpoyTime(Date deployTime) {
        this.deployTime = deployTime;
        return this;
    }

    public ProcessWithVersionResult setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public ProcessWithVersionResult setVersion(int version) {
        this.version = version;
        return this;
    }

}
