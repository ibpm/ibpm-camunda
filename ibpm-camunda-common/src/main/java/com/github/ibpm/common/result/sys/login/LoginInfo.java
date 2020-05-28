package com.github.ibpm.common.result.sys.login;

import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import lombok.*;

import java.util.List;

@ToString
public class LoginInfo {

    /** logged user */
    protected User user;

    /** role*/
    protected List<Role> roles;

    /** resource */
    protected List<String> resources;

    public User getUser() {
        return user;
    }

    public LoginInfo setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public LoginInfo setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public List<String> getResources() {
        return resources;
    }

    public LoginInfo setResources(List<String> resources) {
        this.resources = resources;
        return this;
    }
}
