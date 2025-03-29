package com.example.dockerapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Boolean orderAvailability;

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getPrice() {
        return price;
    }

    public Boolean getOrderAvailability() {
        return orderAvailability;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOrderAvailability(Boolean orderAvailability) {
        this.orderAvailability = orderAvailability;
    }
}
