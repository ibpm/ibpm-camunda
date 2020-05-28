package com.github.ibpm.config.conf;

import com.github.ibpm.config.aspect.MultiDataSourceAspect;
import com.github.ibpm.config.bean.SpringContextAware;
import com.github.ibpm.config.dao.ApiLogMapper;
import com.github.ibpm.config.datasource.DataSourceConfig;
import com.github.ibpm.config.datasource.DataSourceProperties;
import com.github.ibpm.config.exception.GlobalExceptionAdvisor;
import com.github.ibpm.config.id.SnowFlakeIdGenerator;
import com.github.ibpm.config.property.AppProperties;
import com.github.ibpm.config.service.ApiLogService;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@EnableConfigurationProperties({
        DataSourceProperties.class,
        AppProperties.class
})
@Configuration
@ComponentScan(basePackageClasses = {
        DataSourceConfig.class,
        MultiDataSourceAspect.class,

        GlobalExceptionAdvisor.class,

        LocaleConfig.class,
        LocalServerConfig.class,

        SpringContextAware.class,

        ApiLogService.class,
        ApiLogMapper.class
})
public class ConfigConfiguration {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public IdGenerator idGenerator() {
        if (appProperties.getSnowFlake().getDataCenterId() != null &&
                appProperties.getSnowFlake().getMachineId() != null) {
            return new SnowFlakeIdGenerator(
                    appProperties.getSnowFlake().getDataCenterId(),
                    appProperties.getSnowFlake().getMachineId());
        }
        return new StrongUuidGenerator();
    }

    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle", "oracle");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(appProperties.getJucScheduledPoolSize());
    }
}
