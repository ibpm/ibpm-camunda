package com.github.ibpm.security.service;

import com.github.ibpm.common.enums.UserStatus;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.sys.login.LoginParam;
import com.github.ibpm.common.param.sys.user.UserNameParam;
import com.github.ibpm.common.result.sys.login.LoginInfo;
import com.github.ibpm.common.result.sys.login.LoginResult;
import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.sys.ext.jwt.JWTService;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import com.github.ibpm.sys.service.UserService;
import com.github.ibpm.sys.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService extends BaseServiceAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public LoginResult login(LoginParam param) {
        return loginByCondition(param, false);
    }

    public LoginResult loginByEncoded(LoginParam param) {
        return loginByCondition(param, true);
    }

    private LoginResult loginByCondition(LoginParam param, boolean pwdEncoded) {
        User user = userService.get(new UserNameParam().setUserName(param.getUserName()));
        if (user == null) {
            throw new RTException(1300, param.getUserName());
        } else if ((pwdEncoded && !StringUtils.equals(user.getPassword(), param.getPassword()))
                || (!pwdEncoded && !validatePassword(user.getPassword(), param.getPassword(), param.getUserName()))) {
            throw new RTException(1301, user.getUserName());
        }
        if (user.getStatus() == UserStatus.FROZEN.getStatus()) {
            throw new RTException(1302, user.getUserName());
        }
        return new LoginResult().setToken(jwtService.createToken(user));
    }

    public LoginInfo getInfo() {
        UserNameParam getParam = new UserNameParam().setUserName(injectUserName());
        User user = userService.get(getParam);
        List<Role> roles = userService.getRoles(getParam);
        List<String> resources = userService.getResources(getParam);
        user.setPassword(null); // remove password because of security
        return new LoginInfo()
                .setUser(user)
                .setResources(resources)
                .setRoles(roles);
    }

    private boolean validatePassword(String dbPassword, String plainPassword, String userName) {
        String encodedPassword = PasswordUtil.encode(plainPassword, userName);
        return StringUtils.equals(dbPassword, encodedPassword);
    }
}
