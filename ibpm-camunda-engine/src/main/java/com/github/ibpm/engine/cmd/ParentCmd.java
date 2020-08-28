package com.github.ibpm.engine.cmd;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class ParentCmd implements Command<Void> {

   protected JdbcTemplate injectJdbcTemplate(CommandContext commandContext) {
       return new JdbcTemplate(commandContext.getProcessEngineConfiguration().getDataSource());
   }
}
