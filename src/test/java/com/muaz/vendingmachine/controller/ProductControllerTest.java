package com.muaz.vendingmachine.controller;

import com.muaz.vendingmachine.entity.Product;
import com.muaz.vendingmachine.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static com.muaz.vendingmachine.provider.ProductProvider.getMockProduct;
import static com.muaz.vendingmachine.utils.PaymentUtil.asJsonString;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldGetProductListWhenValidRequest() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/product/getAllProducts");
        mockMvc.perform(builder)
                .andExpect(ok);

        verify(productService, timeout(1)).getAllProducts();
    }

    @Test
    public void shouldUploadProductsWhenValidRequest() throws Exception {
        Product product = getMockProduct();
        List<Product> productList = Collections.singletonList(product);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product/uploadProducts")
                .content(asJsonString(productList))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        verify(productService, times(1)).uploadProducts(productList);
        verify(productService, times(1)).deleteAllProducts();
    }
}