package com.recruitment.datalake.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200") // Add your frontend URL here
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
        		.allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files from the 'uploads' directory
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/Users/pc/Documents/workspace-spring-tool-suite-4-4.26.0.RELEASE/datalake/uploads/");
    }

}
