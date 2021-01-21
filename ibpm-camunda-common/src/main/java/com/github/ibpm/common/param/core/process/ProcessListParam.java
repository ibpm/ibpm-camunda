package com.github.ibpm.common.param.core.process;

import com.github.ibpm.common.param.PageSortParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProcessListParam extends PageSortParam {

    protected String processDefinitionKey;

    protected String processDefinitionName;

    protected List<Integer> status;
}
