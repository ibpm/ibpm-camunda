package com.github.ibpm.common.param.core.job;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobCopyParam extends JobNameWithDefinitionParam {

    protected JobSaveParam targetJobParam;

    public String validate() {
        if (targetJobParam == null) {
            return "6081";
        }
        return targetJobParam.validate();
    }

}
