package com.example.dockerapi.service;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("全注文を取得できる")
    public void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(new Order(), new Order()));
        assertThat(orderService.getAllOrders()).hasSize(2);
    }

    @Test
    @DisplayName("注文をIDで取得できる")
    public void testGetOrderById() {
        Order order = new Order();
        order.setOrderId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getOrderId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("注文を作成できる")
    public void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        assertThat(orderService.createOrder(order)).isNotNull();
    }
}
