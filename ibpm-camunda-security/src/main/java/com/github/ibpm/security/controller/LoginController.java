package com.github.ibpm.security.controller;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.sys.login.LoginParam;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.sys.login.LoginInfo;
import com.github.ibpm.common.result.sys.login.LoginResult;
import com.github.ibpm.security.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login")
@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    @Operation(summary = "Login by userName and password", parameters = {@Parameter(name = "param", required = true)})
    @PostMapping(APIPath.LoginPath.$)
    public CommonResult<LoginResult> login(@RequestBody LoginParam param) {
        return CommonResult.putResult(service.login(param));
    }

    @Operation(summary = "Login by userName and encoded password", parameters = {@Parameter(name = "param", required = true)})
    @PostMapping(APIPath.LoginPath.LOGIN_BY_ENCODED_USERNAME_PWD)
    public CommonResult<LoginResult> loginByEncoded(@RequestBody LoginParam param) {
        return CommonResult.putResult(service.loginByEncoded(param));
    }

    /**
     * get by token
     *
     * @return
     */
    @Operation(summary = "Get user&resource&role by token")
    @GetMapping(APIPath.LoginPath.INFO)
    public CommonResult<LoginInfo> getInfo() {
        return CommonResult.putResult(service.getInfo());
    }
}
