package com.github.ibpm.common.result.core.job;

import lombok.*;

@Getter
@Setter
@ToString
public class ArgAllocatedResult {

    protected String argName;

    protected Integer argType;

    protected String argValue;

    protected int allocated;

}
