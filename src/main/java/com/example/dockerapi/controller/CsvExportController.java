package com.example.dockerapi.controller;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.OrderRepository;
import com.example.dockerapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class CsvExportController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 売上CSVを出力するエンドポイント
     */
    @GetMapping("/sales/export")
    public ResponseEntity<String> exportSalesCsv() {
        List<Order> orders = orderRepository.findAll();
        String csvContent = generateSalesCsv(orders);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sales_export.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
    }

    /**
     * 在庫CSVを出力するエンドポイント
     */
    @GetMapping("/inventory/export")
    public ResponseEntity<String> exportInventoryCsv() {
        List<Product> products = productRepository.findAll();
        String csvContent = generateInventoryCsv(products);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventory_export.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
    }

    /**
     * 売上CSVを生成
     */
    private String generateSalesCsv(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        sb.append("注文日,決済日,金額,ユーザー名\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Order order : orders) {
            sb.append(order.getOrderDate().format(formatter)).append(",");
            sb.append(order.getOrderDate().plusDays(1).format(formatter)).append(","); // 仮の決済日
            sb.append(order.getTotalPrice()).append(",");
            sb.append("ユーザー" + order.getUserId()).append("\n"); // 仮のユーザー名
        }
        return sb.toString();
    }

    /**
     * 在庫CSVを生成
     */
    private String generateInventoryCsv(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("商品名,在庫数,金額\n");

        for (Product product : products) {
            sb.append(product.getProductName()).append(",");
            sb.append(product.getStockQuantity()).append(",");
            sb.append(product.getPrice()).append("\n");
        }
        return sb.toString();
    }
}
