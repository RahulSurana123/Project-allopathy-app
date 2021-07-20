package com.allopathy.management.security.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CustomCorsConfiguration extends CorsConfiguration {

	@Override
	public String checkOrigin(String requestOrigin) {
		if(!StringUtils.hasText(requestOrigin)) {
			return null;
		}
		if (ObjectUtils.isEmpty(getAllowedOrigins())) {
            return null;
        }	
		if (getAllowedOrigins().contains(ALL)) {
            if (getAllowCredentials() != Boolean.TRUE) {
                return ALL;
            }
            else {
                return requestOrigin;
            }
        }
        for (String allowedOrigin : getAllowedOrigins()) {
            if (requestOrigin.matches(allowedOrigin)) {
                return requestOrigin;
            }
        }
		return null;
	}

	
}
