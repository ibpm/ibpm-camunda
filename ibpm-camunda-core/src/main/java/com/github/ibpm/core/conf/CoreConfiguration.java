package com.github.ibpm.core.conf;

import com.github.ibpm.core.controller.ServerInfoController;
import com.github.ibpm.core.dao.core.CalendarMapper;
import com.github.ibpm.core.dao.core.JobMapper;
import com.github.ibpm.core.dao.instance.InstanceMapper;
import com.github.ibpm.core.ext.notice.NoticeService;
import com.github.ibpm.core.ext.notice.channel.dingtalk.DingTalkSender;
import com.github.ibpm.core.ext.notice.channel.mail.MailSender;
import com.github.ibpm.core.ext.notice.channel.wxwork.WxWorkSender;
import com.github.ibpm.core.service.core.CalendarService;
import com.github.ibpm.core.service.core.JobService;
import com.github.ibpm.core.service.instance.InstanceService;
import com.github.ibpm.core.service.tool.ToolService;
import com.github.ibpm.engine.conf.EngineConfiguration;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Import({
        EngineConfiguration.class
})
@Configuration
@ComponentScan(basePackageClasses = {
        JobMapper.class,
        InstanceMapper.class,
        CalendarMapper.class,

        //service
        JobService.class,
        ToolService.class,
        InstanceService.class,
        CalendarService.class,

        ServerInfoController.class,

        MailSender.class,
        DingTalkSender.class,
        WxWorkSender.class,
        NoticeService.class,
})
public class CoreConfiguration {

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }

}
