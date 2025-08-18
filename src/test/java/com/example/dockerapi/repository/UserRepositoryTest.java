package com.example.dockerapi.repository;

import com.example.dockerapi.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/demo");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @Test
    @DisplayName("ユーザーを保存・取得できること")
    void testSaveAndFindUser() {
        User user = new User();
        user.setName("テストユーザー");
        user.setAddress("東京都");
        user.setIsMale(true);
        user.setOld(30);

        User saved = userRepository.save(user);
        Optional<User> result = userRepository.findById(saved.getUserId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("テストユーザー");
        assertThat(result.get().getAddress()).isEqualTo("東京都");
        assertThat(result.get().getIsMale()).isTrue();
        assertThat(result.get().getOld()).isEqualTo(30);
    }
}
