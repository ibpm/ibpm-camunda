package com.github.ibpm.common.param.core.instance;

import com.github.ibpm.common.param.CommonParam;
import org.apache.commons.lang3.StringUtils;

public class InstanceGetParam implements CommonParam {

    protected String id;

    protected String processInstanceId;

    @Override
    public String validate() {
        if (StringUtils.isBlank(id)) {
            return "1504";
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public InstanceGetParam setId(String id) {
        this.id = id;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public InstanceGetParam setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }
}
