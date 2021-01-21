package com.github.ibpm.common.result.core.process;

public class ProcessExportAndImportResult {

    protected ProcessModel processModel;

    public ProcessModel getProcess() {
        return processModel;
    }

    public ProcessExportAndImportResult setProcess(ProcessModel processModel) {
        this.processModel = processModel;
        return this;
    }

}
