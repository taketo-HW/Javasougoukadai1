package com.example.dockerapi.controller;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 全注文取得 (JST補正＆フォーマットして返す)
    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getAllOrders().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 単一注文取得 (JST補正＆フォーマットして返す)
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(o -> ResponseEntity.ok(convertToResponse(o)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 注文作成 (保存時は通常Order)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    // 注文更新 (保存時は通常Order)
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
    }

    // 注文削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // ======== 内部変換メソッド ========
    private OrderResponse convertToResponse(Order order) {
        ZonedDateTime jstDateTime = order.getOrderDate()
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneOffset.ofHours(9));
        String formattedDate = jstDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderStatus(),
                order.getUserId(),
                order.getProductId(),
                order.getProductName(),
                order.getTotalPrice(),
                formattedDate);
    }
}
