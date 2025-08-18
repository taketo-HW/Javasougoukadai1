package com.example.dockerapi.controller;

import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("✅ CSV出力：在庫一覧を正しく出力")
    public void testExportInventoryCsv() throws Exception {
        Product mockProduct = new Product();
        mockProduct.setProductId(1L);
        mockProduct.setProductName("Test Product");
        mockProduct.setStockQuantity(10);
        mockProduct.setPrice(999);
        mockProduct.setOrderAvailability(true);

        when(productRepository.findAll()).thenReturn(List.of(mockProduct));

        mockMvc.perform(get("/inventory/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv; charset=UTF-8"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"inventory.csv\""))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("productId")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Test Product")));
    }
}
