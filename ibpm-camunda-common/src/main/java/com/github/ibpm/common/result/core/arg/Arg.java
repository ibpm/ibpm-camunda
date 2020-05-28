package com.github.ibpm.common.result.core.arg;

import lombok.ToString;
import org.apache.commons.lang.StringUtils;

@ToString
public class Arg {

    protected String argName;

    protected Integer argType;

    protected String argValue;

    protected Integer genericType;

    protected String attribute;

    protected String remark;

    public String getArgName() {
        return argName;
    }

    public Arg setArgName(String argName) {
        this.argName = argName;
        return this;
    }

    public Integer getArgType() {
        return argType;
    }

    public Arg setArgType(Integer argType) {
        this.argType = argType;
        return this;
    }

    public String getArgValue() {
        return argValue;
    }

    public Arg setArgValue(String argValue) {
        this.argValue = argValue;
        return this;
    }

    public Integer getGenericType() {
        return genericType;
    }

    public Arg setGenericType(Integer genericType) {
        this.genericType = genericType;
        return this;
    }

    public String getAttribute() {
        return attribute;
    }

    public Arg setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Arg setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public int hashCode() {
        return this.argName.hashCode() * 37;
    }

    @Override
    public boolean equals(Object obj) {
        Arg model = (Arg) obj;
        return StringUtils.equals(this.argName, model.getArgName());
    }
}
