package com.muaz.vendingmachine.entity;

import com.muaz.vendingmachine.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private int productNo;

    @Column
    private int count;

    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
