package com.muaz.vendingmachine.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    private String offerNumber;

    @Column
    private int productNo;

    @Column
    private int count;

    @Column
    private Integer sugar;

    @OneToOne(mappedBy = "offer")
    private PaymentRequest paymentRequest;
}
