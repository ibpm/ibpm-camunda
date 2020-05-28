package com.github.ibpm.sys.service;

import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.sys.role.*;
import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.sys.dao.RoleMapper;
import com.github.ibpm.sys.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleService extends BaseServiceAdapter {

	@Autowired
	private RoleMapper mapper;

	public Map<String, Object> list(RoleListParam param) {
		PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
		Page<Role> list = (Page<Role>) mapper.list(param);
		return PageUtil.toResultMap(list);
	}

	public Role get(RoleNameParam param){
	    return mapper.get(param);
    }

	public Role add(RoleSaveParam param) {
		Role model = get(new RoleNameParam().setRoleName(param.getRoleName()));
		if(model != null){
			throw new DuplicateKeyException(model.getRoleName());
		}
		model = to(param);
		mapper.add(model);
		return model;
	}

	public Role update(RoleSaveParam param) {
		Role role = get(new RoleNameParam().setRoleName(param.getRoleName()));
		if(role == null){
			throw new RTException(5350);
		}
		Role updatedRole = to(param);
        mapper.update(updatedRole);
        return updatedRole;
	}

	public Void remove(RoleNamesParam param) {
		mapper.delete(param.getRoleNames());
		return null;
	}

	public List<User> getUsers(RoleNameParam param) {
		return mapper.getUsers(param);
    }

	public Void saveUsers(RoleUserSaveParam param) {
		mapper.deleteUsers(param);
		if (param.getUserNames() != null && !param.getUserNames().isEmpty()) {
			mapper.addUsers(param);
        }
		return null;
    }

	public List<String> getResources(RoleNameParam param) {
		return mapper.getResources(param);
	}

	public Void saveResources(RoleResourceSaveParam param) {
		mapper.deleteResources(param);
		if (param.getResourceNames() != null && !param.getResourceNames().isEmpty()) {
			mapper.addResources(param);
		}
		return null;
	}

	private Role to(RoleSaveParam param) {
		return new Role()
				.setRoleName(param.getRoleName())
				.setDisplayName(param.getDisplayName())
				.setRemark(param.getRemark());
	}
}
