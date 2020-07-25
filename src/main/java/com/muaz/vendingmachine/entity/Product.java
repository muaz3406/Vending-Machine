package com.muaz.vendingmachine.entity;

import com.muaz.vendingmachine.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
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
