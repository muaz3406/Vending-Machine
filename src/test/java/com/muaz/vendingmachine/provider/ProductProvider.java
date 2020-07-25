package com.muaz.vendingmachine.provider;

import com.muaz.vendingmachine.entity.Product;
import com.muaz.vendingmachine.enums.ProductType;

import java.math.BigDecimal;

public class ProductProvider {
    public static Product getMockProduct() {
        return Product.builder()
                .count(10)
                .productNo(55)
                .name("banana")
                .price(BigDecimal.TEN)
                .productType(ProductType.SOLID)
                .build();
    }
}
