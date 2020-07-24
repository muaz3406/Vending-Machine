package com.muaz.vendingmachine.repository;

import com.muaz.vendingmachine.entity.PaymentResponse;
import org.springframework.data.repository.CrudRepository;

public interface PaymentResponseRepository extends CrudRepository<PaymentResponse, Long> {
}
