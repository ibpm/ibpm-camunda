package com.github.ibpm.sys.store;

import com.github.ibpm.common.result.sys.user.User;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.collections.map.MultiKeyMap;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SysStore {

    private static User adminUser;

    private static Map<String, String> userTokenMap = new ConcurrentHashMap<>();

    public static Map<String, String> getUserTokenMap() {
        return userTokenMap;
    }

    public static User getAdminUser() {
        return adminUser;
    }

    public static void setAdminUser(User adminUser) {
        SysStore.adminUser = adminUser;
    }

}
