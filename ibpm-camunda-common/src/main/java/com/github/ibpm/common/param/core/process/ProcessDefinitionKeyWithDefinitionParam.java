package com.github.ibpm.common.param.core.process;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProcessDefinitionKeyWithDefinitionParam extends ProcessDefinitionKeyParam {

    protected String processDefinitionId;
}
