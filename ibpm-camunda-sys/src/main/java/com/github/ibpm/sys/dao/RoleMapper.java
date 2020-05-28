package com.github.ibpm.sys.dao;

import com.github.ibpm.common.param.sys.role.RoleNameParam;
import com.github.ibpm.common.param.sys.role.RoleListParam;
import com.github.ibpm.common.param.sys.role.RoleResourceSaveParam;
import com.github.ibpm.common.param.sys.role.RoleUserSaveParam;
import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {

    void add(Role model);

    void update(Role model);

    Role get(RoleNameParam param);

    void delete(List<String> roleNames);

    List<Role> list(RoleListParam param);

    List<User> getUsers(RoleNameParam param);

    void deleteUsers(RoleUserSaveParam param);

    void addUsers(RoleUserSaveParam param);

    List<String> getResources(RoleNameParam param);

    void deleteResources(RoleResourceSaveParam param);

    void addResources(RoleResourceSaveParam param);
}
