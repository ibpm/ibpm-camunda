package com.github.ibpm.common.param.sys.user;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

@ToString
public class UserNameParam implements CommonParam {

    protected String userName;

    public String validate() {
        if (StringUtils.isBlank(userName)) {
            return "5100";
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public UserNameParam setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
