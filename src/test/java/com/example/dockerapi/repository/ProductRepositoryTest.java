package com.example.dockerapi.repository;

import com.example.dockerapi.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("商品を保存・取得できること")
    void testSaveAndFindProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(1000);

        Product saved = productRepository.save(product);

        Optional<Product> result = productRepository.findById(saved.getProductId());

        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("Test Product");
        assertThat(result.get().getPrice()).isEqualTo(1000);
    }
}
