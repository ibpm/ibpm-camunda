
package com.github.ibpm.web;

import com.github.ibpm.common.constant.GlobalConstants;
import com.github.ibpm.security.conf.SecurityConfiguration;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@Import({
		SecurityConfiguration.class
})
@EnableAsync
@MapperScan(GlobalConstants.mybatisPlusMapperLocation)
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Ibpm-camunda api doc",
				version = "1.0",
				description = "Bpm using camunda"
		),
		externalDocs = @ExternalDocumentation(description = "User document",
				url = "https://ibpm-camunda.github.io/ibpm-camunda-document/"
		)
)
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}



