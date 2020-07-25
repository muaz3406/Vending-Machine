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
import static com.muaz.vendingmachine.utils.PaymentUtil.isCash;

@Service
@Slf4j
public class OfferService {

    private static final int REMAINING_LIMIT = 0;
    private static final int SMALLER = -1;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentResponseRepository paymentResponseRepository;

    public PaymentResponse doOffer(PaymentRequest paymentRequest, String offerNumber) {
        Offer offer = paymentRequest.getOffer();
        Product product = productRepository.findByProductNo(offer.getProductNo());

        log.info("status: {}, orderNumber: {} ", OFFER_START, offerNumber);

        int remainingCount = product.getCount() - offer.getCount();
        PaymentResponse paymentResponse = createPaymentResponse(paymentRequest, product);

        if (remainingCount < REMAINING_LIMIT) {
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
            return paymentResponse.getRemainingPrice().compareTo(BigDecimal.ZERO) == SMALLER;
        }
        return false;

    }

    private PaymentResponse createPaymentResponse(PaymentRequest paymentRequest, Product product) {
        PaymentType paymentType = paymentRequest.getPaymentType();
        BigDecimal totalPrice = calculateTotalPrice(paymentRequest,product);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .count(paymentRequest.getOffer().getCount())
                .paymentType(paymentType)
                .productName(product.getName())
                .totalPrice(totalPrice)
                .remainingPrice(isCash(paymentType) ? calculateRemainingPrice(paymentRequest, totalPrice) : null)
                .build();

        return paymentResponseRepository.save(paymentResponse);
    }

    private BigDecimal calculateRemainingPrice(PaymentRequest paymentRequest, BigDecimal totalPrice) {
        return paymentRequest.getMoney().subtract(totalPrice);
    }

    private BigDecimal calculateTotalPrice(PaymentRequest paymentRequest, Product product) {
        return product.getPrice().multiply(BigDecimal.valueOf(paymentRequest.getOffer().getCount()));
    }
}
