package com.github.ibpm.core.ext.notice.channel.mail;

import com.github.attemper.alarm.AlarmType;
import com.github.attemper.alarm.mail.MailAlarm;
import com.github.attemper.alarm.mail.MailConfig;
import com.github.attemper.alarm.mail.MailInformation;
import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.util.ReflectUtil;
import com.github.ibpm.core.ext.notice.MessageBean;
import com.github.ibpm.core.ext.notice.channel.Sender;
import com.github.ibpm.core.ext.notice.MessageBean;
import com.github.ibpm.core.ext.notice.channel.Sender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("myMailSender")
public class MailSender implements Sender {

    @Override
    public void send(MessageBean messageBean) throws Exception {
        MailAlarm alarm = new MailAlarm();
        MailConfig config = ReflectUtil.reflectObj(MailConfig.class,
                CommonConstants.KEY_ALARM_ARG + getIndex(),
                messageBean.getExtraMap());
        if (StringUtils.isBlank(messageBean.getTo().getEmail())) {
            log.error("email is blank:{}", messageBean);
            return;
        }
        MailInformation info = new MailInformation()
                .setFrom(messageBean.getFrom())
                .setTo(messageBean.getTo().getEmail())
                .setSubject(messageBean.getSubject())
                .setContent(messageBean.getContent());
        alarm.send(config, info);
    }

    @Override
    public int getIndex() {
        return AlarmType.MAIL.getValue();
    }
}
