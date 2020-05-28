package com.github.ibpm.security.conf;

import com.github.ibpm.core.conf.CoreConfiguration;
import com.github.ibpm.security.controller.LoginController;
import com.github.ibpm.security.service.LoginService;
import com.github.ibpm.security.xss.XssFilter;
import com.github.ibpm.sys.ext.jwt.JWTService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.DelegatingFilterProxy;

@Import({
        CoreConfiguration.class
})
@Configuration
@ComponentScan(basePackageClasses = {
        //conf
        ShiroConfig.class,

        //jwt
        JWTService.class,

        //filter
        XssFilter.class,

        //service
        LoginService.class,

        //controller
        LoginController.class,

})
public class SecurityConfiguration {

    @Bean
    public FilterRegistrationBean shiroFilterRegistrationBean() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filter.setFilter(proxy);
        filter.getInitParameters().put("exclusions", "*.js,*.css,*.gif,*.jpg,*.png,*.html,*.map,*.woff,*.jpeg,*.tif,*.fiff");
        filter.setOrder(Integer.MAX_VALUE - 1);
        filter.addUrlPatterns("/*");
        return filter;
    }
}
