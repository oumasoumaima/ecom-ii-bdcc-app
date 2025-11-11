package com.example.authentication.billingservice.entities;

import com.example.authentication.billingservice.model.Customer;
import com.example.authentication.billingservice.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    private long customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems = new ArrayList<>();
    @Transient private Customer customer; //récupère dynamiquement via Feign pas depuis la bd
}