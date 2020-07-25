package com.muaz.vendingmachine.entity;

import com.muaz.vendingmachine.enums.PaymentType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "paymentRequest")
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    private String cardInfo;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column
    private BigDecimal money;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    private Offer offer;
}
