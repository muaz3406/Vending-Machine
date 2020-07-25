package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.Product;
import com.muaz.vendingmachine.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> uploadProducts(List<Product> productList) {
        return (List<Product>) productRepository.saveAll(productList);
    }
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
