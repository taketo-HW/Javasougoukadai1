package com.example.dockerapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName(" DB接続成功時の応答を検証")
    public void testCheckDbConnection_Success() throws Exception {
        when(jdbcTemplate.queryForObject("SELECT 1", Integer.class)).thenReturn(1);

        mockMvc.perform(get("/api/check-db"))
                .andExpect(status().isOk())
                .andExpect(content().string("Database connection is successful!"));
    }

    @Test
    @DisplayName(" DB接続失敗時の応答を検証")
    public void testCheckDbConnection_Failure() throws Exception {
        when(jdbcTemplate.queryForObject("SELECT 1", Integer.class)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/check-db"))
                .andExpect(status().isOk())
                .andExpect(content().string("Database connection failed!"));
    }
}
