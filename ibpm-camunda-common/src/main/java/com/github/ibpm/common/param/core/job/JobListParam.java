package com.github.ibpm.common.param.core.job;

import com.github.ibpm.common.param.PageSortParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JobListParam extends PageSortParam {

    protected String jobName;

    protected String displayName;

    protected List<Integer> status;
}
