package com.example.dockerapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/", "file:/app/target/classes/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/", "file:/app/target/classes/static/js/");
    }
}
