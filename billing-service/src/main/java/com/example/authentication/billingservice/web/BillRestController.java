package com.example.authentication.billingservice.web;

import com.example.authentication.billingservice.entities.Bill;
import com.example.authentication.billingservice.feign.CustomerRestClient;
import com.example.authentication.billingservice.feign.ProductRestClient;
import com.example.authentication.billingservice.repository.BillRepository;
import com.example.authentication.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerRestClient customerRestClient;
    @Autowired
    private ProductRestClient productRestClient;
    @GetMapping(path = "/bills/{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(productItem -> {
            productItem.setProduct(productRestClient.getProductById(productItem.getProductId()));
        });
        return bill;
    }
    // 🆕 NOUVEAU : Endpoint pour récupérer les factures d'un client
    @GetMapping(path = "/bills")
    public List<Bill> getBillsByCustomer(@RequestParam(required = false) Long customerId){
        if(customerId != null) {
            List<Bill> bills = billRepository.findByCustomerId(customerId);
            bills.forEach(bill -> {
                bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
                bill.getProductItems().forEach(productItem -> {
                    productItem.setProduct(productRestClient.getProductById(productItem.getProductId()));
                });
            });
            return bills;
        }
        // Si pas de customerId, retourner toutes les factures
        return billRepository.findAll();
    }
}