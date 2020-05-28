package com.github.ibpm.common.param.core.job;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobNameWithDefinitionParam extends JobNameParam {

    protected String procDefId;
}
