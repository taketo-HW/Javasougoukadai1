package com.example.dockerapi.controller;

import com.example.dockerapi.model.User;
import com.example.dockerapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private User sampleUser() {
        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe"); // モデルにある name を使用
        user.setAddress("john@example.com"); // モデルにある address を使用
        user.setIsMale(true);
        user.setOld(30);
        return user;
    }

    @Test
    @DisplayName("GET /api/users - 全ユーザー取得")
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(sampleUser()));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].address").value("john@example.com"))
                .andExpect(jsonPath("$[0].isMale").value(true))
                .andExpect(jsonPath("$[0].old").value(30));
    }
}
