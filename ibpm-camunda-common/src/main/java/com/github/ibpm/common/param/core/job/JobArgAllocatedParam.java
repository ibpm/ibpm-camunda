package com.github.ibpm.common.param.core.job;

import com.github.ibpm.common.param.PageSortParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobArgAllocatedParam extends PageSortParam {

    protected String jobName;

    protected String argName;
}
