package com.github.ibpm.engine.cmd.impl;

import com.github.ibpm.engine.cmd.ParentCmd;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class AddDraftCmd extends ParentCmd {

    private static final String SQL_HI_PROCINST =
            "INSERT INTO act_hi_procinst\n" +
                    "(ID_,\n" +
                    "  PROC_INST_ID_,\n" +
                    "  BUSINESS_KEY_,\n" +
                    "  PROC_DEF_KEY_,\n" +
                    "  PROC_DEF_ID_,\n" +
                    "  START_TIME_,\n" +
                    "  START_USER_ID_" +
                    ") VALUES " +
                    "(" +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?" +
                    ")";

    private static final String SQL_RU_EXECUTION =
            "INSERT INTO act_ru_execution\n" +
                    "(ID_,\n" +
                    "  PROC_INST_ID_,\n" +
                    "  BUSINESS_KEY_,\n" +
                    "  PROC_DEF_ID_\n" +
                    ") VALUES " +
                    "(" +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?" +
                    ")";

    private static final String SQL_RU_TASK =
            "INSERT INTO act_ru_task\n" +
                    "(ID_,\n" +
                    "  EXECUTION_ID_,\n" +
                    "  PROC_INST_ID_,\n" +
                    "  PROC_DEF_ID_,\n" +
                    /*"  TASK_DEF_KEY_,\n" +
                    "  NAME_,\n" +*/
                    "  ASSIGNEE_,\n" +
                    "  CREATE_TIME_" +
                    ") VALUES " +
                    "(" +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?" +
                    ")";

    protected String procInstId;

    protected String businessKey;

    protected String processDefinitionKey;

    protected String processDefinitionId;

    protected Date startTime;

    protected String startUserId;

    protected String taskId;

    public AddDraftCmd(String procInstId, String businessKey, String processDefinitionKey, String processDefinitionId, Date startTime, String startUserId, String taskId) {
        this.procInstId = procInstId;
        this.businessKey = businessKey;
        this.processDefinitionKey = processDefinitionKey;
        this.processDefinitionId = processDefinitionId;
        this.startTime = startTime;
        this.startUserId = startUserId;
        this.taskId = taskId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        JdbcTemplate jdbcTemplate = super.injectJdbcTemplate(commandContext);
        jdbcTemplate.update(SQL_HI_PROCINST, procInstId, procInstId, businessKey, processDefinitionKey, processDefinitionId, startTime, startUserId);
        jdbcTemplate.update(SQL_RU_EXECUTION, procInstId, procInstId, businessKey, processDefinitionId);
        jdbcTemplate.update(SQL_RU_TASK, taskId, procInstId, procInstId, processDefinitionId, startUserId, startTime);
        return null;
    }
}
