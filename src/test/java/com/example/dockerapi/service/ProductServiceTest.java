package com.example.dockerapi.service;

import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("全商品を取得できる")
    public void testGetAllProducts() {
        Product p1 = new Product();
        Product p2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        assertThat(productService.getAllProducts()).hasSize(2);
    }

    @Test
    @DisplayName("商品をIDで取得できる")
    public void testGetProductById() {
        Product product = new Product();
        product.setProductId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getProductId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("商品を作成できる")
    public void testCreateProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        assertThat(productService.createProduct(product)).isNotNull();
    }
}
