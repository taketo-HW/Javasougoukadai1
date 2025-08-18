package com.example.dockerapi.controller;

public class OrderResponse {
    private Long orderId;
    private int orderStatus;
    private Long userId;
    private Long productId;
    private String productName;
    private int totalPrice;
    private String orderDate; // JST補正済みフォーマットされた文字列

    // コンストラクタ
    public OrderResponse(Long orderId, int orderStatus, Long userId, Long productId,
            String productName, int totalPrice, String orderDate) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // Getter
    public Long getOrderId() {
        return orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
