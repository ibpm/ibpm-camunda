package com.github.ibpm.biz.conf;


import com.github.ibpm.biz.dao.Demo01Mapper;
import com.github.ibpm.biz.dao.Demo02Mapper;
import com.github.ibpm.biz.service.Demo01Service;
import com.github.ibpm.biz.service.Demo02Service;
import com.github.ibpm.engine.conf.EngineConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        EngineConfiguration.class
})
@Configuration
@ComponentScan(basePackageClasses = {
        Demo01Mapper.class,
        Demo02Mapper.class,

        //service
        Demo01Service.class,
        Demo02Service.class,

})
public class BizConfiguration {
}
