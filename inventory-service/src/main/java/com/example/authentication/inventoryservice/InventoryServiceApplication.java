package com.example.authentication.inventoryservice;

import com.example.authentication.inventoryservice.entities.Product;
import com.example.authentication.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return args -> {
            productRepository.save(Product.builder()
                    .id("1")
                    .name("Computer")
                    .price(3200)
                    .quantity(11)
                    .build());
            productRepository.save(Product.builder()
                    .id("2")
                    .name("Printer")
                    .price(1299)
                    .quantity(10)
                    .build());
            productRepository.save(Product.builder()
                    .id("3")
                    .name("Smart Phone")
                    .price(5400)
                    .quantity(8)
                    .build());
            productRepository.save(Product.builder()
                    .id("4")
                    .name("Servers")
                    .price(24000)
                    .quantity(6)
                    .build());
            productRepository.save(Product.builder()
                    .id("5")
                    .name("Tablets")
                    .price(8000)
                    .quantity(18)
                    .build());
            productRepository.save(Product.builder()
                    .id("6")
                    .name("Smart Watch")
                    .price(8000)
                    .quantity(20)
                    .build());

            productRepository.findAll().forEach(p->{
                System.out.println(p.toString());
            });
        };
    }

}