package com.muaz.vendingmachine.repository;

import com.muaz.vendingmachine.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByProductNo(int productNo);
}
