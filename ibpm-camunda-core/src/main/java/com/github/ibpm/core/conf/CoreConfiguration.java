package com.github.ibpm.core.conf;

import com.github.ibpm.core.controller.ServerInfoController;
import com.github.ibpm.core.dao.core.CalendarMapper;
import com.github.ibpm.core.dao.core.ProcessMapper;
import com.github.ibpm.core.ext.notice.NoticeService;
import com.github.ibpm.core.ext.notice.channel.dingtalk.DingTalkSender;
import com.github.ibpm.core.ext.notice.channel.mail.MailSender;
import com.github.ibpm.core.ext.notice.channel.wxwork.WxWorkSender;
import com.github.ibpm.core.service.core.CalendarService;
import com.github.ibpm.core.service.core.ProcessService;
import com.github.ibpm.core.service.tool.ToolService;
import com.github.ibpm.engine.conf.EngineConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        EngineConfiguration.class
})
@Configuration
@ComponentScan(basePackageClasses = {
        ProcessMapper.class,
        CalendarMapper.class,

        //service
        ProcessService.class,
        ToolService.class,
        CalendarService.class,

        ServerInfoController.class,

        MailSender.class,
        DingTalkSender.class,
        WxWorkSender.class,
        NoticeService.class,
})
public class CoreConfiguration {

}
