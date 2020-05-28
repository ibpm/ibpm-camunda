package com.github.ibpm.config.conf;

import com.github.ibpm.common.property.StatusProperty;
import com.github.ibpm.common.property.TipProperty;
import com.github.ibpm.config.property.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(-1)
@Component
public class LocaleConfig implements ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    private AppProperties appProperties;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        StatusProperty.initResourceBundle(appProperties.getLocaleInfo().getLanguage(), appProperties.getLocaleInfo().getCountry());
        TipProperty.initResourceBundle(appProperties.getLocaleInfo().getLanguage(), appProperties.getLocaleInfo().getCountry());
    }
}
