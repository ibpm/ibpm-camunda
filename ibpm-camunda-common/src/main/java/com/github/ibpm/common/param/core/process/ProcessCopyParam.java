package com.github.ibpm.common.param.core.process;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProcessCopyParam extends ProcessDefinitionKeyWithDefinitionParam {

    protected ProcessSaveParam targetProcessParam;

    public String validate() {
        if (targetProcessParam == null) {
            return "6081";
        }
        return targetProcessParam.validate();
    }

}
