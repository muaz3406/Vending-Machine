package com.muaz.vendingmachine.entity;

import com.muaz.vendingmachine.enums.PaymentType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity(name = "PaymentRequest")
@Table(name = "paymentRequest")
public class PaymentRequest {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String CardInfo;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column
    private BigDecimal money;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Order order;
}
