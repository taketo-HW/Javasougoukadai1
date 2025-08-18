package com.example.dockerapi.controller;

import com.example.dockerapi.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import com.example.dockerapi.model.Order;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalesController.class)
public class SalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // まだ正式導入ではないが、こうなる可能性がある
    @MockitoBean
    private OrderRepository orderRepository;

    @Test
    @DisplayName(" 正常系：売上CSV出力が成功する")
    public void testExportSalesCsv_Success() throws Exception {
        // Mockデータ
        Order mockOrder = new Order();
        mockOrder.setOrderId(1L);
        mockOrder.setOrderDate(LocalDateTime.of(2025, 4, 6, 10, 0));
        mockOrder.setOrderStatus(1);
        mockOrder.setProductId(1L);
        mockOrder.setProductName("Product X");
        mockOrder.setTotalPrice(1234);
        mockOrder.setUserId(1L); // 修正箇所

        when(orderRepository.findAll()).thenReturn(List.of(mockOrder));

        mockMvc.perform(get("/sales/export")
                .param("year", "2025")
                .param("month", "4")
                .param("day", "6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv; charset=UTF-8"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"sales_2025-04-06.csv\""));
    }

    @Test
    @DisplayName(" 異常系：不正な日付を指定した場合はalertを返す")
    public void testExportSalesCsv_InvalidDate() throws Exception {
        mockMvc.perform(get("/sales/export")
                .param("year", "2025")
                .param("month", "4")
                .param("day", "99")) // 不正な日
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("alert('日付を正しく入力してください。')")));
    }
}
