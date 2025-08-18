package com.example.dockerapi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    void testOrderEntity() {
        Order order = new Order();
        order.setOrderId(10L); // Long型に変更
        order.setOrderDate(LocalDateTime.of(2025, 3, 14, 10, 0));
        order.setOrderStatus(1);
        order.setProductId(5L); // Long型に変更
        order.setProductName("モニター");
        order.setTotalPrice(30000);
        order.setUserId(2L); // Long型に変更

        assertThat(order.getOrderId()).isEqualTo(10L);
        assertThat(order.getOrderDate()).isEqualTo(LocalDateTime.of(2025, 3, 14, 10, 0));
        assertThat(order.getOrderStatus()).isEqualTo(1);
        assertThat(order.getProductId()).isEqualTo(5L);
        assertThat(order.getProductName()).isEqualTo("モニター");
        assertThat(order.getTotalPrice()).isEqualTo(30000);
        assertThat(order.getUserId()).isEqualTo(2L);
    }
}
