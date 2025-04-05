package com.example.dockerapi.service;

import com.example.dockerapi.model.User;
import com.example.dockerapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("全ユーザーを取得できる")
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        assertThat(userService.getAllUsers()).hasSize(2);
    }

    @Test
    @DisplayName("ユーザーをIDで取得できる")
    public void testGetUserById() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getUserId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("ユーザーを作成できる")
    public void testCreateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        assertThat(userService.createUser(user)).isNotNull();
    }
}
