package com.example.dockerapi.repository;

import com.example.dockerapi.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("ユーザーを保存・取得できること")
    void testSaveAndFindUser() {
        // ユーザーを作成
        User user = new User();
        user.setName("Taro");
        user.setAddress("taro@example.com");

        // 保存
        User saved = userRepository.save(user);

        // IDによる取得
        Optional<User> result = userRepository.findById(saved.getUserId());

        // 検証
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Taro");
        assertThat(result.get().getAddress()).isEqualTo("taro@example.com");
    }
}
