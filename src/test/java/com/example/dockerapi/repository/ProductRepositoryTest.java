package com.example.dockerapi.repository;

import com.example.dockerapi.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/demo");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @Test
    @DisplayName("商品を保存・取得できること")
    void testSaveAndFindProduct() {
        Product product = new Product();
        product.setProductName("テスト商品");
        product.setStockQuantity(20);
        product.setPrice(1200);
        product.setOrderAvailability(true);

        Product saved = productRepository.save(product);
        Optional<Product> result = productRepository.findById(saved.getProductId());

        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("テスト商品");
        assertThat(result.get().getPrice()).isEqualTo(1200);
    }
}
