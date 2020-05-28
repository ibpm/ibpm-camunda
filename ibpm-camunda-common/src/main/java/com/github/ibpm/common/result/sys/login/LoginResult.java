package com.github.ibpm.common.result.sys.login;

/**
 * @author ldang
 */
public class LoginResult {

    protected String token;

    public String getToken() {
        return token;
    }

    public LoginResult setToken(String token) {
        this.token = token;
        return this;
    }
}
