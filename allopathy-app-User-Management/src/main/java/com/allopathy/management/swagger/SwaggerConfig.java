package com.allopathy.management.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	public static final Contact DEFAULT_CONTACT = new Contact("Rahul Surana",
			"allopathy", "rasuru04@gmail.com");
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
			"Project Management Service", "Project management APIs", "1.0",
			"urn:tos", DEFAULT_CONTACT, "", "",
			new ArrayList<VendorExtension>());
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
			Arrays.asList("application/json", "appllication/xml"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO)
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
}