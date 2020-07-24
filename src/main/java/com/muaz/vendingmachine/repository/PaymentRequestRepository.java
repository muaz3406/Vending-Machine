package com.muaz.vendingmachine.repository;

import com.muaz.vendingmachine.entity.PaymentRequest;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRequestRepository extends CrudRepository<PaymentRequest, Long> {
}
