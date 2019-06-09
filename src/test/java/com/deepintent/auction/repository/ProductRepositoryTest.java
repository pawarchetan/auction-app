package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @Before
    public void setUp() {
        product = persistProduct();
    }

    @After
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldCreateProductWithAGivenMetadata() {
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals("M Docklands", product.getName());
        assertEquals("677 LaTrobe St., Docklands - 3008", product.getAddress());
        assertEquals("House", product.getDescription());
        assertEquals(BigDecimal.valueOf(350000.00), product.getPrice());
    }


    @Test
    public void shouldReturnAllProducts() {
        List<Product> productList = productRepository.findAll();

        assertNotNull(productList);
        assertEquals(1, productList.size());
    }

    @Test
    public void shouldUpdateProduct() {
        product.setId(product.getId());
        product.setPrice(BigDecimal.valueOf(40000.00));

        Product updatedProduct = productRepository.save(product);

        assertEquals(BigDecimal.valueOf(40000.00), updatedProduct.getPrice());
    }

    @Test
    public void shouldDeleteProductForAGivenId() {
        productRepository.deleteById(product.getId());
        Optional<Product> deletedProduct = productRepository.findById(product.getId());

        assertFalse(deletedProduct.isPresent());
    }

    private Product persistProduct() {
        return TestData.createProduct(productRepository);
    }

}
