package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.Product;
import com.muaz.vendingmachine.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.muaz.vendingmachine.provider.ProductProvider.getMockProduct;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void shouldUploadProductListWithGivenProductList() {
        List<Product> productList =Collections.singletonList(getMockProduct());
        productService.uploadProducts(productList);

        verify(productRepository).saveAll(productList);
    }

    @Test
    public void shouldGetAllProductWhenInvokeFindAllProduct() {
        List<Product> productList =Collections.singletonList(getMockProduct());
        when(productRepository.findAll()).thenReturn(productList);

        assertEquals(productList, productService.getAllProducts());
    }
}