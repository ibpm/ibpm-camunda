package com.github.ibpm.biz.model;

import lombok.Getter;

@Getter
public class Demo01Data {

    private String businessKey;

    private long startDate;

    private int num;

    private String destination;

    private String tool;

    private String reason;

    public Demo01Data setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public Demo01Data setStartDate(long startDate) {
        this.startDate = startDate;
        return this;
    }

    public Demo01Data setNum(int num) {
        this.num = num;
        return this;
    }

    public Demo01Data setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Demo01Data setTool(String tool) {
        this.tool = tool;
        return this;
    }

    public Demo01Data setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
