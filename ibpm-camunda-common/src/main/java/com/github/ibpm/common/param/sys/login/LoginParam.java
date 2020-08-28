package com.github.ibpm.common.param.sys.login;

import com.github.ibpm.common.param.CommonParam;
import org.apache.commons.lang3.StringUtils;

public class LoginParam implements CommonParam {

    protected String userName;

    protected String password;

    public String validate() {
        if(StringUtils.isBlank(userName)){
            return "5100";
        }

        if(StringUtils.isBlank(password)) {
            return "5101";
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public LoginParam setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginParam setPassword(String password) {
        this.password = password;
        return this;
    }
}
