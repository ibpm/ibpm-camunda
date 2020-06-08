package com.github.ibpm.engine.model;

import java.io.InputStream;

public class BpmnResource {

    private String resourceName;

    private InputStream inputStream;

    public String getResourceName() {
        return resourceName;
    }

    public BpmnResource setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public BpmnResource setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }
}
