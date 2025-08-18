package com.example.dockerapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

public class CorsConfigTest {
    @Test
    public void testCorsConfigurerBeanExists() {
        try (AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext()) {
            context.setServletContext(new MockServletContext());
            context.register(CorsConfig.class);
            context.refresh();

            WebMvcConfigurer configurer = (WebMvcConfigurer) context.getBean("corsConfigurer");
            assertThat(configurer).isNotNull();
        }
    }
}
