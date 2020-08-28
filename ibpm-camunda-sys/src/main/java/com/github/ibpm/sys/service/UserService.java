package com.github.ibpm.sys.service;

import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.sys.user.*;
import com.github.ibpm.common.result.sys.role.Role;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.sys.dao.UserMapper;
import com.github.ibpm.sys.store.SysStore;
import com.github.ibpm.sys.util.PageUtil;
import com.github.ibpm.sys.util.PasswordUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService extends BaseServiceAdapter {

    @Autowired
    private UserMapper mapper;

    public User get(UserNameParam param) {
        return mapper.get(param.getUserName());
    }

    /**
     * get super admin
     *
     * @return
     */
    public User getAdmin() {
        return mapper.getAdmin();
    }

    public Map<String, Object> list(UserListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<User> list = (Page<User>) mapper.list(paramMap);
        return PageUtil.toResultMap(list);
    }

    public User add(UserSaveParam param) {
        User user = get(new UserNameParam().setUserName(param.getUserName()));
        if(user != null){
            throw new DuplicateKeyException(param.getUserName());
        }
        user = toUser(param);
        user.setPassword(PasswordUtil.encode(user.getPassword(), user.getUserName()))
                .setSuperAdmin(0); // just add common user
        mapper.add(user);
        return user;
    }

    public User update(UserSaveParam param) {
        User user = validateAndGet(param.getUserName());
        if (!StringUtils.equals(user.getPassword(), PasswordUtil.encode(param.getPassword(), param.getUserName()))) {
            throw new RTException(1301);
        }
        user = toUser(param);
        mapper.update(user);
        return user;
    }

    public Void updatePassword(UserChangePasswordParam param) {
        User user = validateAndGet(param.getUserName());
        String oldEncodedPassword = PasswordUtil.encode(param.getOldPassword(), param.getUserName());
        if (!StringUtils.equals(oldEncodedPassword, user.getPassword())) {
            throw new RTException(5105);
        }
        String newEncodedPassword = PasswordUtil.encode(param.getNewPassword(), param.getUserName());
        if (StringUtils.equals(oldEncodedPassword, newEncodedPassword)) {
            throw new RTException(5106);
        }
        if (!StringUtils.equals(param.getNewPassword(), param.getDoubleNewPassword())) {
            throw new RTException(5107);
        }
        user.setPassword(newEncodedPassword);
        mapper.updatePassword(BeanUtil.bean2Map(user));
        return null;
    }

    public User validateAndGet(String userName) {
        User user = get(new UserNameParam().setUserName(userName));
        if(user == null){
            throw new RTException(5150, userName);
        }
        return user;
    }

    public Void remove(UserNamesParam param) {
        for (String userName : param.getUserNames()) {
            if (StringUtils.equals(userName, SysStore.getAdminUser().getUserName())) {
                throw new RTException(5115, userName);
            }
        }
        mapper.delete(param.getUserNames());
        return null;
    }

    public List<String> getResources(UserNameParam param) {
        return mapper.getResources(BeanUtil.bean2Map(param));
    }

    public List<Role> getRoles(UserNameParam param) {
        return mapper.getRoles(BeanUtil.bean2Map(param));
    }

    public Void saveRoles(UserRoleSaveParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.deleteRoles(paramMap);
        if (param.getRoleNames() != null && param.getRoleNames().size() > 0) {
            mapper.addRoles(paramMap);
        }
        return null;
    }

    private User toUser(UserSaveParam param) {
        return new User()
                .setUserName(param.getUserName())
                .setDisplayName(param.getDisplayName())
                .setPassword(param.getPassword())
                .setEmail(param.getEmail())
                .setMobile(param.getMobile())
                .setStatus(param.getStatus())
                .setSendConfig(param.getSendConfig());
    }

}
