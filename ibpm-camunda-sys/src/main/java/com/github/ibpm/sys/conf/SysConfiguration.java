package com.github.ibpm.sys.conf;

import com.github.ibpm.config.conf.ConfigConfiguration;
import com.github.ibpm.sys.aspect.ControllerAspect;
import com.github.ibpm.sys.controller.RoleController;
import com.github.ibpm.sys.controller.UserController;
import com.github.ibpm.sys.dao.RoleMapper;
import com.github.ibpm.sys.dao.UserMapper;
import com.github.ibpm.sys.exception.SysExceptionAdvisor;
import com.github.ibpm.sys.service.RoleService;
import com.github.ibpm.sys.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ConfigConfiguration.class
})
@ComponentScan(basePackageClasses = {
        SysExceptionAdvisor.class,

        ControllerAspect.class,

        //mapper
        UserMapper.class,
        RoleMapper.class,

        //service
        UserService.class,
        RoleService.class,

        //controller
        UserController.class,
        RoleController.class,
})
public class SysConfiguration {

}
