package com.example.dockerapi.service;

import com.example.dockerapi.model.User;
import com.example.dockerapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User baseUser;

    @BeforeEach
    void setUp() {
        baseUser = new User();
        baseUser.setName("初期ユーザー");
        baseUser.setAddress("大阪府");
        baseUser.setIsMale(false);
        baseUser.setOld(25);
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/demo");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @Test
    @DisplayName("ユーザーを作成できること")
    void testCreateUser() {
        User saved = userService.createUser(baseUser);
        assertThat(saved.getUserId()).isNotNull();
        assertThat(userRepository.findById(saved.getUserId())).isPresent();
    }

    @Test
    @DisplayName("ユーザーをIDで取得できること")
    void testGetUserById() {
        User saved = userService.createUser(baseUser);
        Optional<User> result = userService.getUserById(saved.getUserId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("初期ユーザー");
    }

    @Test
    @DisplayName("ユーザーを更新できること")
    void testUpdateUser() {
        User saved = userService.createUser(baseUser);

        User updated = new User();
        updated.setName("更新ユーザー");
        updated.setAddress("福岡県");
        updated.setIsMale(true);
        updated.setOld(40);

        User result = userService.updateUser(saved.getUserId(), updated);

        assertThat(result.getName()).isEqualTo("更新ユーザー");
        assertThat(result.getAddress()).isEqualTo("福岡県");
        assertThat(result.getIsMale()).isTrue();
        assertThat(result.getOld()).isEqualTo(40);
    }

    @Test
    @DisplayName("ユーザーを削除できること")
    void testDeleteUser() {
        User saved = userService.createUser(baseUser);
        Long id = saved.getUserId();

        userService.deleteUser(id);
        assertThat(userService.getUserById(id)).isEmpty();
    }
}
