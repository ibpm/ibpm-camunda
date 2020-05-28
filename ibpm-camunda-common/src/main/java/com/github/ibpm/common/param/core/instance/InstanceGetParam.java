package com.github.ibpm.common.param.core.instance;

import com.github.ibpm.common.param.CommonParam;
import org.apache.commons.lang.StringUtils;

public class InstanceGetParam implements CommonParam {

    protected String id;

    protected String procInstId;

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

    public String getProcInstId() {
        return procInstId;
    }

    public InstanceGetParam setProcInstId(String procInstId) {
        this.procInstId = procInstId;
        return this;
    }
}
