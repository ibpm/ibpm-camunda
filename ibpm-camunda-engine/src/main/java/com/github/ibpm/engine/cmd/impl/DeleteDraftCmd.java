package com.github.ibpm.engine.cmd.impl;

import com.github.ibpm.engine.cmd.ParentCmd;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class DeleteDraftCmd extends ParentCmd {

    private static final String SQL_HI_PROCINST =
            "DELETE FROM act_hi_procinst WHERE PROC_INST_ID_ = ?";

    private static final String SQL_RU_TASK =
            "DELETE FROM act_ru_task WHERE PROC_INST_ID_ = ?";

    private static final String SQL_RU_EXECUTION =
            "DELETE FROM act_ru_execution WHERE PROC_INST_ID_ = ?";

    protected String processInstanceId;

    public DeleteDraftCmd(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        JdbcTemplate jdbcTemplate = super.injectJdbcTemplate(commandContext);
        jdbcTemplate.update(SQL_HI_PROCINST, processInstanceId);
        jdbcTemplate.update(SQL_RU_TASK, processInstanceId);
        jdbcTemplate.update(SQL_RU_EXECUTION, processInstanceId);
        return null;
    }
}
