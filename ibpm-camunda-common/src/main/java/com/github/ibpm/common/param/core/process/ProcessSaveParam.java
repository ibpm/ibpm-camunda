package com.github.ibpm.common.param.core.process;

import com.github.ibpm.common.enums.ProcessStatus;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
public class ProcessSaveParam extends ProcessContentSaveParam {

    protected String processDefinitionName;

    protected Integer status;

    protected String remark;

    public String validate() {
        if (StringUtils.isBlank(processDefinitionName)){
            return "6003";
        }
        if (ProcessStatus.get(status) == null) {
            return "6004";
        }
        return super.validate();
    }

    public ProcessSaveParam setProcessDefinitionName(String displayName) {
        this.processDefinitionName = displayName;
        return this;
    }

    public ProcessSaveParam setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ProcessSaveParam setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
