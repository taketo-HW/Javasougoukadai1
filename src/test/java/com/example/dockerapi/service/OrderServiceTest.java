package com.example.dockerapi.service;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private Order baseOrder;

    @BeforeEach
    void setUp() {
        baseOrder = new Order();
        baseOrder.setOrderDate(LocalDateTime.now());
        baseOrder.setOrderStatus(0);
        baseOrder.setTotalPrice(5000);
        baseOrder.setUserId(100L);
        baseOrder.setProductId(200L);
        baseOrder.setProductName("テスト商品");
    }

    // テスト内でMySQLへの接続情報を明示的に指定
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/demo");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @Test
    @DisplayName("注文を新規作成できること")
    void testCreateOrder() {
        Order saved = orderService.createOrder(baseOrder);
        assertThat(saved.getOrderId()).isNotNull();
        assertThat(orderRepository.findById(saved.getOrderId())).isPresent();
    }

    @Test
    @DisplayName("注文をIDで取得できること")
    void testGetOrderById() {
        Order saved = orderService.createOrder(baseOrder);
        Optional<Order> result = orderService.getOrderById(saved.getOrderId());
        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("テスト商品");
    }

    @Test
    @DisplayName("注文を更新できること")
    void testUpdateOrder() {
        Order saved = orderService.createOrder(baseOrder);
        Order updated = new Order();
        updated.setOrderDate(LocalDateTime.now());
        updated.setOrderStatus(2);
        updated.setTotalPrice(9999);
        updated.setUserId(300L);
        updated.setProductId(400L);
        updated.setProductName("更新商品");

        Order result = orderService.updateOrder(saved.getOrderId(), updated);

        assertThat(result.getOrderStatus()).isEqualTo(2);
        assertThat(result.getProductName()).isEqualTo("更新商品");
    }

    @Test
    @DisplayName("注文を削除できること")
    void testDeleteOrder() {
        Order saved = orderService.createOrder(baseOrder);
        Long id = saved.getOrderId();

        orderService.deleteOrder(id);
        assertThat(orderService.getOrderById(id)).isEmpty();
    }
}
