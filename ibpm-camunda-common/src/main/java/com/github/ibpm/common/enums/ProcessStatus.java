package com.github.ibpm.common.enums;

public enum ProcessStatus {

    ENABLED(0),

    DISABLED(1),

    ;

    private int status;

    ProcessStatus(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

    public static ProcessStatus get(int status) {
        for (ProcessStatus item : ProcessStatus.values()) {
            if(item.getStatus() == status){
                return item;
            }
        }
        return null;
    }
}
