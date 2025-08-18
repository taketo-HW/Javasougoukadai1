package com.example.dockerapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "test"; // templates/test.html を表示
    }

    @GetMapping("/main")
    public String mainPage() {
        return "index"; // templates/index.html を表示
    }

    @GetMapping("/orders")
    public String ordersPage() {
        return "orders"; // templates/orders.html を表示
    }

    @GetMapping("/products")
    public String productsPage() {
        return "products"; // templates/products.html を表示
    }

    @GetMapping("/inventory_export")
    public String inventory_export() {
        return "inventory_export"; // templates/inventory_export.html を表示
    }

    @GetMapping("/sales_export")
    public String sales_export() {
        return "sales_export"; // templates/sales_export.html を表示
    }
}
