package com.example.dockerapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Inventory {

    @Id
    private Long id;
    private String name;
    private Integer quantity;
    private String location;
}
