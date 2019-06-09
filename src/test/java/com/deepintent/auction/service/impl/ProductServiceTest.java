package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;
import com.deepintent.auction.repository.ProductRepository;
import com.deepintent.auction.repository.TestData;
import com.deepintent.auction.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void shouldCreateProduct() {
        ProductDto productDto = TestData.getProductDto();
        Product dummyProduct = TestData.createDummyProduct();
        when(productRepository.save(any())).thenReturn(dummyProduct);

        Product product = productService.createProduct(productDto);

        assertNotNull(product);
        assertEquals(dummyProduct, product);
    }

    @Test
    public void shouldReturnAllProducts() {
        List<Product> products = TestData.getAllProducts();
        when(productRepository.findAll()).thenReturn(products);

        List<Product> productList = productService.getAllProducts();

        assertNotNull(productList);
        assertEquals(1, productList.size());
    }

    @Test
    public void shouldUpdateProduct() {
        ProductDto productDto = TestData.getProductDto();
        Product dummyProduct = TestData.createDummyProduct();
        when(productRepository.save(any())).thenReturn(dummyProduct);

        Product product = productService.updateProduct(productDto);

        assertNotNull(product);
        assertEquals(dummyProduct, product);
    }

    @Test
    public void shouldDeleteProductById() {
        doNothing().when(productRepository).deleteById("test-id");
        productService.deleteProduct("test-id");
    }
}
