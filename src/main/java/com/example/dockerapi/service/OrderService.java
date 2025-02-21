package com.example.dockerapi.service;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // すべての注文を取得
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 注文IDで検索
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // 注文を作成
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // 注文情報を更新
    public Order updateOrder(Long id, Order newOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderStatus(newOrder.getOrderStatus());
            order.setTotalPrice(newOrder.getTotalPrice());
            order.setOrderDate(newOrder.getOrderDate());
            order.setUserId(newOrder.getUserId());
            order.setProductId(newOrder.getProductId());
            order.setProductName(newOrder.getProductName());
            return orderRepository.save(order);
        }).orElse(null);
    }

    // 注文を削除
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
