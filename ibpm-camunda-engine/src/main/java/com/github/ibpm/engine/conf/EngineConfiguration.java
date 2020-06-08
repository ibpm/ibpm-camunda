package com.github.ibpm.engine.conf;

import com.github.ibpm.config.id.MyIdGenerator;
import com.github.ibpm.sys.conf.SysConfiguration;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        SysConfiguration.class
})
@Configuration
public class EngineConfiguration {

    @Autowired
    private MyIdGenerator myIdGenerator;

    @Bean
    public IdGenerator idGenerator() {
        return () -> myIdGenerator.getNextId();
    }
}
