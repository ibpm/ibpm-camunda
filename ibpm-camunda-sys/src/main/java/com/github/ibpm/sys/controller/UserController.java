package com.github.ibpm.sys.controller;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.sys.user.*;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.sys.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name ="User")
@RestController
public class UserController {
	
	@Autowired
	private UserService service;

    @RequiresPermissions("user-add")
    @Operation(summary = "Add user", parameters = {@Parameter(name = "param", required = true)})
	@PostMapping(APIPath.UserPath.$)
    public CommonResult<User> add(@RequestBody UserSaveParam param) {
        return CommonResult.putResult(service.add(param));
    }

    @RequiresPermissions("user-update")
    @Operation(summary = "Update user", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.UserPath.$)
    public CommonResult<User> update(@RequestBody UserSaveParam param) {
        return CommonResult.putResult(service.update(param));
    }

	@RequiresPermissions("user-update")
	@Operation(summary = "Update password", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.UserPath.PASSWORD)
	public CommonResult<Void> updatePassword(@RequestBody UserChangePasswordParam param) {
		return CommonResult.putResult(service.updatePassword(param));
	}

	@Operation(summary = "List users", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.UserPath.$)
	public CommonResult<Map<String, Object>> list(UserListParam param) {
		return CommonResult.putResult(service.list(param));
	}

    @RequiresPermissions("user-remove")
    @Operation(summary = "Remove users", parameters = {@Parameter(name = "param", required = true)})
	@DeleteMapping(APIPath.UserPath.$)
    public CommonResult<Void> remove(@RequestBody UserNamesParam param) {
        return CommonResult.putResult(service.remove(param));
    }

	@Operation(summary = "Get user", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.UserPath.GET)
	public CommonResult<User> get(UserNameParam param) {
		User user = service.get(param);
		user.setPassword(null);
		return CommonResult.putResult(user);
    }

	@Operation(summary = "Get role", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.UserPath.ROLE)
	public CommonResult<List<Role>> getRoles(UserNameParam getParam) {
		return CommonResult.putResult(service.getRoles(getParam));
	}

	@Operation(summary = "Update role", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.UserPath.ROLE)
	public CommonResult saveRoles(@RequestBody UserRoleSaveParam param) {
		return CommonResult.putResult(service.saveRoles(param));
	}
}
