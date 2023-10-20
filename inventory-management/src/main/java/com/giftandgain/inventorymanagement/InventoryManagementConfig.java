package com.giftandgain.inventorymanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InventoryManagementConfig {
	
//	 @Bean
//	    public WebMvcConfigurer corsConfigurer() {
//	        return new WebMvcConfigurer() {
//	            @Override
//	            public void addCorsMappings(CorsRegistry registry) {
//	                registry.addMapping("/**")
//	                        .allowedOrigins("http://localhost:9001")
//							.allowedOrigins("http://frontend-balancer-601654883.us-east-1.elb.amazonaws.com:9060/")
//	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//	                        .allowedHeaders("*")
//	                        .allowCredentials(true);
//	            }
//	        };
//	    }

}
