package com.github.ibpm.engine.conf;

import com.github.ibpm.sys.conf.SysConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        SysConfiguration.class
})
@Configuration
public class EngineConfiguration {

}
