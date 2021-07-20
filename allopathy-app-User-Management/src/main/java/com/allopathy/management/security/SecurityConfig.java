package com.allopathy.management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.allopathy.management.constant.SecurityConstants;
import com.allopathy.management.security.cors.CustomCorsConfiguration;
import com.allopathy.management.service.impl.UserDetailsServiceImpl;
import com.google.common.collect.ImmutableList;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;

//	@Autowired
//	TokenServiceImpl tokenServiceImpl;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeRequests()
				.antMatchers("api/v1/user").permitAll()
				.antMatchers("/v2/api-docs", "/configuration/**",
						"/swagger*/**", "/webjars/**", "/actuator/**",
						"/api/v1/public/users/**", "/api/v1/users/refreshToken")
				.permitAll()
//				.anyRequest().authenticated()
				.and().requestCache()
				.requestCache(new NullRequestCache())
//				.and().addFilter(new JWTTokenFilter(authenticationManagerBean(),
//						tokenServiceImpl, userDetailsService))
				;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CustomCorsConfiguration();
		configuration
				.setAllowedOrigins(ImmutableList.of(environment.getProperty(
						SecurityConstants.PROP_CORS_ACCESS_CONTROL_ALLOW_ORIGIN,
						"*")));
		configuration.setAllowedMethods(ImmutableList.of("GET", "POST",
				"DELETE", "PUT", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(
				ImmutableList.of("Content-Type", "Accept", "Accept-Encoding",
						"Content-Encoding", "Authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
