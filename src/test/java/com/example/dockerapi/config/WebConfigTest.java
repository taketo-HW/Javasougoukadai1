package com.example.dockerapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;

import static org.mockito.Mockito.*;

public class WebConfigTest {

    @Test
    public void testAddResourceHandlers() {
        WebConfig config = new WebConfig();

        ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);

        when(registry.addResourceHandler("/css/**")).thenReturn(registration);
        when(registry.addResourceHandler("/js/**")).thenReturn(registration);

        config.addResourceHandlers(registry);

        verify(registry).addResourceHandler("/css/**");
        verify(registry).addResourceHandler("/js/**");
    }
}
