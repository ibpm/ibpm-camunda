package com.github.ibpm.common.param.core.arg;

import com.github.ibpm.common.param.PageSortParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArgListParam extends PageSortParam {

    protected String argName;

    protected Integer argType;

    protected String argValue;

    protected String remark;
}
