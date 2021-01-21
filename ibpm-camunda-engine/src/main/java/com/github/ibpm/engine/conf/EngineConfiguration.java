package com.github.ibpm.engine.conf;

import com.github.ibpm.config.id.MyIdGenerator;
import com.github.ibpm.engine.controller.BpmController;
import com.github.ibpm.engine.dao.InstanceMapper;
import com.github.ibpm.engine.service.BpmService;
import com.github.ibpm.engine.service.IbpmEngineService;
import com.github.ibpm.engine.service.instance.InstanceService;
import com.github.ibpm.sys.conf.SysConfiguration;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        SysConfiguration.class,
        CustomCamundaConfiguration.class,
        SpringEventBusListener.class
})
@Configuration
@ComponentScan(basePackageClasses = {
        InstanceMapper.class,

        IbpmEngineService.class,
        InstanceService.class,
        BpmService.class,

        BpmController.class,
})
public class EngineConfiguration {

    @Autowired
    private MyIdGenerator myIdGenerator;

    @Bean
    public IdGenerator idGenerator() {
        return () -> myIdGenerator.getNextId();
    }
}
