package com.example.dockerapi.service;

import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product baseProduct;

    @BeforeEach
    void setUp() {
        baseProduct = new Product();
        baseProduct.setProductName("ベース商品");
        baseProduct.setStockQuantity(10);
        baseProduct.setPrice(3000);
        baseProduct.setOrderAvailability(true);
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3306/demo");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "root");
    }

    @Test
    @DisplayName("商品を作成できること")
    void testCreateProduct() {
        Product saved = productService.createProduct(baseProduct);
        assertThat(saved.getProductId()).isNotNull();
        assertThat(productRepository.findById(saved.getProductId())).isPresent();
    }

    @Test
    @DisplayName("商品をIDで取得できること")
    void testGetProductById() {
        Product saved = productService.createProduct(baseProduct);
        Optional<Product> result = productService.getProductById(saved.getProductId());
        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("ベース商品");
    }

    @Test
    @DisplayName("商品を更新できること")
    void testUpdateProduct() {
        Product saved = productService.createProduct(baseProduct);
        Product updated = new Product();
        updated.setProductName("更新商品");
        updated.setStockQuantity(99);
        updated.setPrice(9999);
        updated.setOrderAvailability(false);

        Product result = productService.updateProduct(saved.getProductId(), updated);

        assertThat(result.getProductName()).isEqualTo("更新商品");
        assertThat(result.getStockQuantity()).isEqualTo(99);
        assertThat(result.getOrderAvailability()).isFalse();
    }

    @Test
    @DisplayName("商品を削除できること")
    void testDeleteProduct() {
        Product saved = productService.createProduct(baseProduct);
        Long id = saved.getProductId();
        productService.deleteProduct(id);
        assertThat(productService.getProductById(id)).isEmpty();
    }
}
