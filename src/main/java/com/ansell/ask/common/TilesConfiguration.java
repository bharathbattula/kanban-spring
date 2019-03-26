package com.ansell.ask.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class TilesConfiguration {

	@Bean
	public UrlBasedViewResolver viewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
	
	@Bean
	public TilesConfigurer configurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles/tiles-def.xml"});
		return tilesConfigurer;
	}
	
	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException{
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", 
				  "org.apache.velocity.runtime.resource.loader." + 
				  "ClasspathResourceLoader");
		factory.setVelocityProperties(props);
		
		return factory.createVelocityEngine();
	}
}
