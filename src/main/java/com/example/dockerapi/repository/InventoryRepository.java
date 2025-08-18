package com.example.dockerapi.repository;

import com.example.dockerapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Product, Long> {
}
