package com.example.dockerapi.repository;

import com.example.dockerapi.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    // MySQL接続を明示的に指定
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/demo");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @Test
    @DisplayName("注文を保存・取得できること（MySQL）")
    void testSaveAndFindOrder() {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(1);
        order.setProductId(1001L);
        order.setProductName("テスト商品");
        order.setTotalPrice(9999);
        order.setUserId(501L);

        Order saved = orderRepository.save(order);
        Optional<Order> result = orderRepository.findById(saved.getOrderId());

        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("テスト商品");
        assertThat(result.get().getTotalPrice()).isEqualTo(9999);
    }
}
