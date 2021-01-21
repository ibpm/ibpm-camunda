package com.github.ibpm.common.param.core.process;

import lombok.ToString;

@ToString
public class ProcessContentSaveParam extends ProcessDefinitionKeyParam {

    protected String content;

    public String getContent() {
        return content;
    }

    public ProcessContentSaveParam setContent(String content) {
        this.content = content;
        return this;
    }

}
