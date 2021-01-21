package com.github.ibpm.common.param.core.instance;

import com.github.ibpm.common.param.CommonParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
public class InstanceIdParam implements CommonParam {

    protected String processInstanceId;

    @Override
    public String validate() {
        if (StringUtils.isBlank(processInstanceId)) {
            return "6200";
        }
        return null;
    }
}
