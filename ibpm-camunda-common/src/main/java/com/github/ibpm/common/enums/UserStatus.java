package com.github.ibpm.common.enums;

public enum UserStatus {

    NORMAL(0),

    /**
     * frozen
     */
    FROZEN(1),

    /**
     * disabled
     */
    DISABLED(2);

    private int status;

    UserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static UserStatus get(int status) {
        for (UserStatus item : UserStatus.values()) {
            if (item.getStatus() == status) {
                return item;
            }
        }
        return null;
    }
}
