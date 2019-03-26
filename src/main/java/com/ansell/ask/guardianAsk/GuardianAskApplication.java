/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * GuardianAskApplication.java
 * Created on 8-Feb-2019
 */


package com.ansell.ask.guardianAsk;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="com.ansell.ask")
public class GuardianAskApplication extends SpringBootServletInitializer{

	
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
		SpringApplication.run(GuardianAskApplication.class, args);
	}
	
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
	        @Override
	        public void customize(Connector connector) {
	            connector.setProperty("relaxedQueryChars", "|{}[]");
	        }
	    });
	    return factory;
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.setProperty("spring.devtools.restart.enabled", "false");
        return application.sources(GuardianAskApplication.class);
    }
	
	/*@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource2
	      = new ReloadableResourceBundleMessageSource();	     
	    messageSource2.setBasename("resource2");
	    messageSource2.setDefaultEncoding("UTF-8");
	    return messageSource2;
	}

	@Bean(name="resource2")
	public DBResourceBundle resources(){		
		DBResourceBundle re = new DBResourceBundle();
		return re;
	}	
	
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}*/
	
	
	
}

