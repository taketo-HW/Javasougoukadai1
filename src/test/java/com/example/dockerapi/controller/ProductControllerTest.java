package com.example.dockerapi.controller;

import com.example.dockerapi.model.Product;
import com.example.dockerapi.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private Product sampleProduct() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Test Product");
        product.setPrice(1000);
        product.setStockQuantity(10);
        product.setOrderAvailability(true);
        return product;
    }

    @Test
    @DisplayName("GET /api/products - 一覧取得")
    void testGetProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(sampleProduct()));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @Test
    @DisplayName("GET /api/products/{id} - 単体取得")
    void testGetProduct() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(sampleProduct()));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    @DisplayName("POST /api/products - 登録")
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(sampleProduct());

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "productName": "Test Product",
                        "price": 1000,
                        "stockQuantity": 10,
                        "orderAvailability": true
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    @DisplayName("PUT /api/products/{id} - 更新")
    void testUpdateProduct() throws Exception {
        when(productService.updateProduct(Mockito.eq(1L), any(Product.class))).thenReturn(sampleProduct());

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "productName": "Updated Product",
                        "price": 2000,
                        "stockQuantity": 20,
                        "orderAvailability": false
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product")); // mockでは常に "Test Product" を返しているため
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - 削除")
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
