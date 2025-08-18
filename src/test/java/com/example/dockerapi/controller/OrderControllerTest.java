package com.example.dockerapi.controller;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private Order sampleOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(1);
        order.setProductId(1L);
        order.setProductName("Test Product");
        order.setTotalPrice(1234);
        order.setUserId(1L);
        return order;
    }

    @Test
    @DisplayName("GET /api/orders - 注文一覧取得")
    void testGetOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(sampleOrder()));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @Test
    @DisplayName("GET /api/orders/{id} - 注文取得")
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(sampleOrder()));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    @DisplayName("POST /api/orders - 注文作成")
    void testCreateOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(sampleOrder());

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "orderStatus": 1,
                        "productId": 1,
                        "productName": "Test Product",
                        "totalPrice": 1234,
                        "userId": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    @DisplayName("PUT /api/orders/{id} - 注文更新")
    void testUpdateOrder() throws Exception {
        when(orderService.updateOrder(Mockito.eq(1L), any(Order.class))).thenReturn(sampleOrder());

        mockMvc.perform(put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "orderStatus": 1,
                        "productId": 1,
                        "productName": "Test Product",
                        "totalPrice": 1234,
                        "userId": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    @DisplayName("DELETE /api/orders/{id} - 注文削除")
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}
