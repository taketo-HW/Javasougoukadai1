package com.example.dockerapi.controller;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.OrderRepository;
import com.example.dockerapi.repository.ProductRepository;

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.CsvWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
public class CsvExportController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/sales/export")
    public ResponseEntity<byte[]> exportSalesCsv(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String day) {

        if (year == null || month == null || day == null ||
                year.isBlank() || month.isBlank() || day.isBlank()) {
            return ResponseEntity.badRequest().body("日付未入力".getBytes(StandardCharsets.UTF_8));
        }

        List<Order> orders = orderRepository.findAll();
        String csvContent = generateSalesCsv(orders);
        byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sales_export.csv");
        headers.setContentType(new MediaType("text", "csv", StandardCharsets.UTF_8));

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/inventory/export")
    public ResponseEntity<byte[]> exportInventoryCsv() {
        List<Product> products = productRepository.findAll();
        String csvContent = generateInventoryCsv(products);
        byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventory_export.csv");
        headers.setContentType(new MediaType("text", "csv", StandardCharsets.UTF_8));

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/api/sales")
    public List<Order> getSalesData() {
        return orderRepository.findAll();
    }

    @GetMapping("/api/inventory")
    public List<Product> getInventoryData() {
        return productRepository.findAll();
    }

    private String generateSalesCsv(List<Order> orders) {
        CsvConfig config = new CsvConfig();
        config.setQuoteDisabled(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (StringWriter writer = new StringWriter();
                CsvWriter csvWriter = new CsvWriter(writer, config)) {

            csvWriter.writeValues(Arrays.asList("注文日", "決済日", "金額", "ユーザー名"));

            for (Order order : orders) {
                String orderDate = order.getOrderDate() != null ? order.getOrderDate().format(formatter) : "null";
                String paymentDate = order.getOrderDate() != null ? order.getOrderDate().plusDays(1).format(formatter)
                        : "null";

                csvWriter.writeValues(Arrays.asList(
                        orderDate,
                        paymentDate,
                        String.valueOf(order.getTotalPrice()),
                        "ユーザー" + order.getUserId()));
            }

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("売上CSV生成エラー", e);
        }
    }

    private String generateInventoryCsv(List<Product> products) {
        CsvConfig config = new CsvConfig();
        config.setQuoteDisabled(false);

        try (StringWriter writer = new StringWriter();
                CsvWriter csvWriter = new CsvWriter(writer, config)) {

            csvWriter.writeValues(Arrays.asList("商品名", "在庫数", "金額"));

            for (Product product : products) {
                csvWriter.writeValues(Arrays.asList(
                        product.getProductName(),
                        String.valueOf(product.getStockQuantity()),
                        String.valueOf(product.getPrice())));
            }

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("在庫CSV生成エラー", e);
        }
    }
}
