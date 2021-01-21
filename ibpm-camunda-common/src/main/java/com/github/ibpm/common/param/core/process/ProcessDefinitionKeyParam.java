package com.github.ibpm.common.param.core.process;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString
public class ProcessDefinitionKeyParam implements CommonParam {

    protected String processDefinitionKey;

    public String validate() {
        if (StringUtils.isBlank(processDefinitionKey)) {
            return "6000";
        }
        return null;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public ProcessDefinitionKeyParam setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }
}
