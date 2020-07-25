package com.muaz.vendingmachine.entity;

import com.muaz.vendingmachine.enums.PaymentType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "paymentResponse")
public class PaymentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    private String productName;

    @Column
    private int count;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column
    private BigDecimal totalPrice;

    @Column
    private BigDecimal remainingPrice;

}
