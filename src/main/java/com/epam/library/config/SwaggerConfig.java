package com.epam.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	
	@Autowired
	SwaggerCustomUIConfig uiconfig;
	
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_12)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.epam.library"))
				.paths(PathSelectors.any())
				.build();
	}
	

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title(uiconfig.getTitle())
				.description(uiconfig.getDescription())
				.version(uiconfig.getVersion())
				.contact(new Contact(uiconfig.getContactname(), uiconfig.getContacturl(),uiconfig.getContactemail()))
				.license(uiconfig.getLicence())
				.licenseUrl(uiconfig.getLicenceurl())
				.build();
	}
	
	
}
