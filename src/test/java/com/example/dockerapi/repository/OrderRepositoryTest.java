package com.example.dockerapi.repository;

import com.example.dockerapi.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("注文を保存・取得できること")
    void testSaveAndFindOrder() {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now()); // 修正点①
        order.setOrderStatus(1); // 修正点②
        order.setProductId(1L);
        order.setProductName("注文商品");
        order.setTotalPrice(5000);
        order.setUserId(1L);

        Order saved = orderRepository.save(order);

        Optional<Order> result = orderRepository.findById(saved.getOrderId());

        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("注文商品");
        assertThat(result.get().getTotalPrice()).isEqualTo(5000);
    }
}
