package com.github.ibpm.biz.model;

import lombok.Getter;

@Getter
public class Demo02Data {

    private String businessKey;

    private long startDate;

    private int num;

    private String reason;

    public Demo02Data setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public Demo02Data setStartDate(long startDate) {
        this.startDate = startDate;
        return this;
    }

    public Demo02Data setNum(int num) {
        this.num = num;
        return this;
    }

    public Demo02Data setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
