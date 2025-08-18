package com.example.dockerapi.controller;

import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.ProductRepository;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.CsvWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Arrays;

@RestController
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/inventory/export")
    public void exportInventoryCsv(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"inventory.csv\"");

        List<Product> products = productRepository.findAll();

        CsvConfig config = new CsvConfig();
        config.setQuote('"');
        config.setSeparator(',');
        config.setEscape('\\');
        config.setQuoteDisabled(false);
        config.setLineSeparator("\r\n");

        CsvWriter writer = new CsvWriter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8),
                config);
        // ヘッダー
        // ヘッダー
        writer.writeValues(Arrays.asList("productId", "productName", "stockQuantity", "price", "orderAvailability"));

        // データ出力
        for (Product item : products) {
            writer.writeValues(Arrays.asList(
                    String.valueOf(item.getProductId()),
                    item.getProductName(),
                    String.valueOf(item.getStockQuantity()),
                    String.valueOf(item.getPrice()),
                    String.valueOf(item.getOrderAvailability())));
        }

        writer.close();

    }
}
