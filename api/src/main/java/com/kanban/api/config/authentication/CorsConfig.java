package com.kanban.api.config.authentication;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
				.allowedHeaders("*")
//				.allowedHeaders("Access-Control-Allow-Headers, Access-Control-Request-Method, X-Requested-With, Content-Type, Cookie, Origin, Authorization")
				.allowCredentials(true)
				.maxAge(3600);
	}
}
