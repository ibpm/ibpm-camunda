package com.github.ibpm.sys.service;

import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.sys.holder.UserHolder;

import java.util.Map;

public abstract class BaseServiceAdapter implements BaseService {

    @Override
    public String injectUserName() {
        return UserHolder.get() != null ? UserHolder.get().getUserName() : null;
    }

    private Map<String, Object> obj2Map(Object obj) {
        if (!(obj instanceof Map)) {
            return BeanUtil.bean2Map(obj);
        } else {
            return (Map<String, Object>) obj;
        }
    }
}
