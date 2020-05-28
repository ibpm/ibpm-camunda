package com.github.ibpm.sys.dao;

import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    void add(User model);

    void update(User model);

    List<User> list(Map<String, Object> paramMap);

    User get(String userName);

    void delete(List<String> userNames);

    User getAdmin();

    List<String> getResources(Map<String, Object> paramMap);

    List<Role> getRoles(Map<String, Object> paramMap);

    void deleteRoles(Map<String, Object> paramMap);

    void addRoles(Map<String, Object> paramMap);

    void updatePassword(Map<String, Object> paramMap);
}
