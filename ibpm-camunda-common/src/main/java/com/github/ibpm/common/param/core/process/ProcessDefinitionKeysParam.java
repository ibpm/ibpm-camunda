package com.github.ibpm.common.param.core.process;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;

import java.util.List;

@ToString
public class ProcessDefinitionKeysParam implements CommonParam {

    protected List<String> processDefinitionKeys;

    public String validate() {
        if(processDefinitionKeys == null || processDefinitionKeys.isEmpty()){
            return "6000";
        }
        return null;
    }

    public List<String> getProcessDefinitionKeys() {
        return processDefinitionKeys;
    }

    public ProcessDefinitionKeysParam setProcessDefinitionKeys(List<String> processDefinitionKeys) {
        this.processDefinitionKeys = processDefinitionKeys;
        return this;
    }
}
