package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.Offer;
import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.entity.Product;
import com.muaz.vendingmachine.enums.PaymentType;
import com.muaz.vendingmachine.exception.NoSuchResourceFoundException;
import com.muaz.vendingmachine.repository.PaymentResponseRepository;
import com.muaz.vendingmachine.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.muaz.vendingmachine.enums.PaymentLogStatus.OFFER_FAIL;
import static com.muaz.vendingmachine.enums.PaymentLogStatus.OFFER_START;

@Service
@Slf4j
public class OfferService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentResponseRepository paymentResponseRepository;

    public PaymentResponse doOrder(PaymentRequest paymentRequest, String offerNumber) {
        Offer offer = paymentRequest.getOffer();
        Product product = productRepository.findByProductNo(offer.getProductNo());

        log.info("status: {}, orderNumber: {} ", OFFER_START, offerNumber);

        int remainingCount = product.getCount() - offer.getCount();
        PaymentResponse paymentResponse = createPaymentResponse(paymentRequest, product);

        if (remainingCount < 0) {
            log.info("status: {}, orderNumber: {} ", OFFER_FAIL, offerNumber);
            throw new NoSuchResourceFoundException("HIGH COUNT REQUESTED");
        }
        if (isNotAffordable(paymentResponse)) {
            log.info("status: {}, orderNumber: {} ", OFFER_FAIL, offerNumber);
            throw new NoSuchResourceFoundException("LESS MONEY");
        }
        productUpdate(product, remainingCount);
        return paymentResponse;
    }

    private void productUpdate(Product product, int remainingCount) {
        product.setCount(remainingCount);
        productRepository.save(product);
    }

    private boolean isNotAffordable(PaymentResponse paymentResponse) {
        if (isCash(paymentResponse.getPaymentType())) {
            return paymentResponse.getRemainingPrice().compareTo(BigDecimal.ZERO) == -1;
        }
        return false;

    }

    private PaymentResponse createPaymentResponse(PaymentRequest paymentRequest, Product product) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setProductName(product.getName());
        paymentResponse.setCount(paymentRequest.getOffer().getCount());
        paymentResponse.setPaymentType(paymentRequest.getPaymentType());
        paymentResponse.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(paymentRequest.getOffer().getCount())));

        if (isCash(paymentRequest.getPaymentType())) {
            paymentResponse.setRemainingPrice(paymentRequest.getMoney().subtract(paymentResponse.getTotalPrice()));
        }
        return paymentResponseRepository.save(paymentResponse);
    }

    private boolean isCash(PaymentType paymentType) {
        return paymentType == PaymentType.BANKNOTE || paymentType == PaymentType.COIN;
    }
}
