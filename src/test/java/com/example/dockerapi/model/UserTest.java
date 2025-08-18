package com.example.dockerapi.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    void testUserEntity() {
        User user = new User();
        user.setUserId(1L);
        user.setName("田中 太郎");
        user.setAddress("東京都港区");
        user.setOld(30);
        user.setIsMale(true);

        assertThat(user.getUserId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("田中 太郎");
        assertThat(user.getAddress()).isEqualTo("東京都港区");
        assertThat(user.getOld()).isEqualTo(30);
        assertThat(user.getIsMale()).isTrue();
    }
}
