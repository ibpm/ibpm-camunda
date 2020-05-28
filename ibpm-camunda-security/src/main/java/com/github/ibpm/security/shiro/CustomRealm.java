package com.github.ibpm.security.shiro;

import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.sys.user.UserNameParam;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.config.bean.SpringContextAware;
import com.github.ibpm.security.model.JWTToken;
import com.github.ibpm.sys.ext.jwt.JWTService;
import com.github.ibpm.sys.holder.UserHolder;
import com.github.ibpm.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        User user = SpringContextAware.getBean(JWTService.class).parseToken(token);
        User dbUser = SpringContextAware.getBean(UserService.class).get(new UserNameParam().setUserName(user.getUserName()));
        if (dbUser == null) {
            throw new RTException(1500, user.getUserName());
        }
        UserHolder.set(dbUser);
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        Set<String> resourcePermissions = new HashSet<>();
        UserService userService = SpringContextAware.getBean(UserService.class);
        List<String> resources = userService.getResources(new UserNameParam().setUserName(user.getUserName()));
        resources.forEach(resource -> resourcePermissions.add(resource));
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(resourcePermissions);
        return simpleAuthorizationInfo;
    }

}
