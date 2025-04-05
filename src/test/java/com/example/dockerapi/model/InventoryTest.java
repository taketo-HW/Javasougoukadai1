package com.example.dockerapi.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTest {

    @Test
    void testInventoryEntity() {
        Inventory inventory = new Inventory();
        inventory.setId(1L); // Long型に修正済み
        inventory.setName("マウス"); // 正しいプロパティ
        inventory.setQuantity(100); // 数量
        inventory.setLocation("あいうえお"); // 位置

        assertThat(inventory.getId()).isEqualTo(1L);
        assertThat(inventory.getName()).isEqualTo("マウス");
        assertThat(inventory.getQuantity()).isEqualTo(100);
        assertThat(inventory.getLocation()).isEqualTo("あいうえお");
    }
}
