package com.github.ibpm.common.enums;

public enum InstanceStatus {

    // draft
    DRAFT(0),

    // running normally
    RUNNING(1),

    // rejected and go back
    REJECTED(2),

    // process is handled
    DONE(-1),

    // cancelled
    TERMINATED(-2),

    // removed
    REMOVED(-3),

    ;

    private int status;

    InstanceStatus(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

    public static InstanceStatus get(int status) {
        for (InstanceStatus item : InstanceStatus.values()) {
            if(item.getStatus() == status){
                return item;
            }
        }
        return null;
    }
}
