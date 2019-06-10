package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.repository.ProductRepository;
import com.deepintent.auction.repository.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private ProductDto productDto;
    private Product dummyProduct;

    @Before
    public void setUp() {
        productDto = TestData.getProductDto();
        dummyProduct = TestData.createDummyProduct();
    }

    @Test
    public void shouldCreateProduct() {
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
        when(productRepository.findById("id")).thenReturn(Optional.ofNullable(dummyProduct));
        when(productRepository.save(any())).thenReturn(dummyProduct);

        Product product = productService.updateProduct(productDto);

        assertNotNull(product);
        assertEquals(dummyProduct, product);
    }

    @Test
    public void shouldDeleteProductById() {
        when(productRepository.findById("id")).thenReturn(Optional.ofNullable(dummyProduct));
        doNothing().when(productRepository).deleteById("id");
        Boolean isDeleted = productService.deleteProduct("id");

        assertTrue(isDeleted);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfProductNotExistWhileUpdating() {
        when(productRepository.findById("id")).thenReturn(Optional.empty());
        productService.updateProduct(productDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfProductNotExistWhileDeleting() {
        when(productRepository.findById("id")).thenReturn(Optional.empty());
        productService.deleteProduct("id");
    }
}
