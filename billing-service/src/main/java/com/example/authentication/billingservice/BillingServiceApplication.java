package com.example.authentication.billingservice;

import com.example.authentication.billingservice.entities.Bill;
import com.example.authentication.billingservice.entities.ProductItem;
import com.example.authentication.billingservice.feign.CustomerRestClient;
import com.example.authentication.billingservice.feign.ProductRestClient;
import com.example.authentication.billingservice.model.Customer;
import com.example.authentication.billingservice.model.Product;
import com.example.authentication.billingservice.repository.BillRepository;
import com.example.authentication.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BillRepository billRepository,
                                        ProductItemRepository productItemRepository,
                                        CustomerRestClient customerRestClient,
                                        ProductRestClient productRestClient) {

        return args -> {
            // Nettoyer les anciennes données
            productItemRepository.deleteAll();
            billRepository.deleteAll();

            Collection<Customer> customers = customerRestClient.getAllCustomers().getContent();
            List<Product> products = new ArrayList<>(productRestClient.getAllProducts().getContent());

            Random random = new Random();

            customers.forEach(customer -> {
                Bill bill = Bill.builder()
                        .billingDate(new Date())
                        .customerId(customer.getId())
                        .build();
                billRepository.save(bill);

                // Sélectionner entre 2 et 4 produits
                int numberOfProducts = 2 + random.nextInt(3);
                Collections.shuffle(products);

                products.stream()
                        .limit(numberOfProducts)
                        .forEach(product -> {
                            ProductItem productItem = ProductItem.builder()
                                    .bill(bill)
                                    .productId(product.getId())
                                    .quantity(1 + random.nextInt(10))
                                    .unitPrice(product.getPrice())
                                    .build();
                            productItemRepository.save(productItem);
                        });

                System.out.println("Bill for " + customer.getName() +
                        " with " + numberOfProducts + " products");
            });
        };
    }
}