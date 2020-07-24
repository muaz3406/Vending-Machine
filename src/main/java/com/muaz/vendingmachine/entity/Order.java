package com.muaz.vendingmachine.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Order")
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String orderNumber;

    @Column
    private int productNo;

    @Column
    private int count;

    @Column
    private int sugar;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentRequest_id")
    private PaymentRequest paymentRequest;
}
