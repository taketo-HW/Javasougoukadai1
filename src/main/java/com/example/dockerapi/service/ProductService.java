package com.example.dockerapi.service;

import com.example.dockerapi.model.Product;
import com.example.dockerapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // すべての商品を取得
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 商品IDで検索
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 商品を作成
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 商品情報を更新
    public Product updateProduct(Long id, Product newProduct) {
        return productRepository.findById(id).map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setStockQuantity(newProduct.getStockQuantity());
            product.setPrice(newProduct.getPrice());
            product.setOrderAvailability(newProduct.getOrderAvailability());
            return productRepository.save(product);
        }).orElse(null);
    }

    // 商品を削除
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
