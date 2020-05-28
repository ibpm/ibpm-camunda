package com.github.ibpm.common.param.core.instance;

import com.github.ibpm.common.param.CommonParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

@Getter
@Setter
@ToString
public class InstanceIdParam implements CommonParam {

    protected String procInstId;

    @Override
    public String validate() {
        if (StringUtils.isBlank(procInstId)) {
            return "6200";
        }
        return null;
    }
}
