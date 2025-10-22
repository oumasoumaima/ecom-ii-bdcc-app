package com.example.authentication.inventoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Builder @ToString @Data
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;
}
