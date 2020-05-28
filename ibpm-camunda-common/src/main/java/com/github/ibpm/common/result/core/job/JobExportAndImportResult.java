package com.github.ibpm.common.result.core.job;

import java.util.List;

public class JobExportAndImportResult {

    protected Job job;

    protected List<String> args;

    public Job getJob() {
        return job;
    }

    public JobExportAndImportResult setJob(Job job) {
        this.job = job;
        return this;
    }

    public List<String> getArgs() {
        return args;
    }

    public JobExportAndImportResult setArgs(List<String> args) {
        this.args = args;
        return this;
    }

}
