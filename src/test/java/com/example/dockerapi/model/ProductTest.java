package com.example.dockerapi.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    void testProductEntity() {
        Product product = new Product();
        product.setProductId(101L);
        product.setProductName("ノートパソコン");
        product.setStockQuantity(50);
        product.setPrice(120000);
        product.setOrderAvailability(true);

        assertThat(product.getProductId()).isEqualTo(101L);
        assertThat(product.getProductName()).isEqualTo("ノートパソコン");
        assertThat(product.getStockQuantity()).isEqualTo(50);
        assertThat(product.getPrice()).isEqualTo(120000);
        assertThat(product.getOrderAvailability()).isTrue();
    }
}
