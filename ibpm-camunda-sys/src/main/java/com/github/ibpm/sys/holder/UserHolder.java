package com.github.ibpm.sys.holder;

import com.github.ibpm.common.result.sys.user.User;

/**
 * the user which user admin it
 */
public class UserHolder {
    
    private static final ThreadLocal<User> contextHolder = new ThreadLocal<User>();

    public static void set(User user) {
        contextHolder.set(user);
    }

    public static User get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
