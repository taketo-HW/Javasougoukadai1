package com.example.dockerapi.repository;

import com.example.dockerapi.model.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("在庫を保存・取得できること")
    void testSaveAndFindInventory() {
        Inventory inventory = new Inventory();
        inventory.setName("テスト商品");
        inventory.setQuantity(100);
        inventory.setLocation("東京");

        Inventory saved = inventoryRepository.save(inventory);

        Optional<Inventory> result = inventoryRepository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("テスト商品");
        assertThat(result.get().getQuantity()).isEqualTo(100);
        assertThat(result.get().getLocation()).isEqualTo("東京");
    }
}
