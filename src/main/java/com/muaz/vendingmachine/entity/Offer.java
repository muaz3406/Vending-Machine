package com.muaz.vendingmachine.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    private String orderNumber;

    @Column
    private int productNo;

    @Column
    private int count;

    @Column
    private int sugar;

    @OneToOne(mappedBy = "offer")
    private PaymentRequest paymentRequest;
}
