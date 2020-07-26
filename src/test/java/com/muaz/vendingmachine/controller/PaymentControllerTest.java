package com.muaz.vendingmachine.controller;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.muaz.vendingmachine.provider.PaymentRequestProvider.getMockCardPaymentRequest;
import static com.muaz.vendingmachine.utils.PaymentUtil.asJsonString;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void shouldPayWhenWhenValidRequest() throws Exception {
        PaymentRequest paymentRequest = getMockCardPaymentRequest();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/payment/doPay")
                .content(asJsonString(paymentRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        verify(paymentService).doPay(paymentRequest);
    }

}