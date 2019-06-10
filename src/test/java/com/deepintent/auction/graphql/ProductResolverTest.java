package com.deepintent.auction.graphql;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;
import com.deepintent.auction.repository.TestData;
import com.deepintent.auction.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductResolverTest {

    @InjectMocks
    private ProductResolver productResolver;

    @Mock
    private ProductService productService;

    @Test
    public void shouldResolveAndCallCreateProductService() {
        ProductDto productDto = TestData.getProductDto();
        Product dummyProduct = TestData.createDummyProduct();
        when(productService.createProduct(any())).thenReturn(dummyProduct);

        Product product = productResolver.createProduct(productDto);
        assertNotNull(product);
        assertEquals(dummyProduct, product);
    }

    @Test
    public void shouldResolveAndCallGetAllProductService() {
        List<Product> products = TestData.getAllProducts();
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> productList = productResolver.getAllProducts();

        assertNotNull(productList);
        assertEquals(1, productList.size());
    }

    @Test
    public void shouldResolveAndCallUpdateProductService() {
        ProductDto productDto = TestData.getProductDto();
        Product dummyProduct = TestData.createDummyProduct();
        when(productService.updateProduct(any())).thenReturn(dummyProduct);

        Product product = productResolver.updateProduct(productDto);

        assertNotNull(product);
        assertEquals(dummyProduct, product);
    }

    @Test
    public void shouldResolveAndCallDeleteProductService() {
        when(productService.deleteProduct("id")).thenReturn(true);
        Boolean isDeleted = productResolver.deleteProduct("id");

        assertTrue(isDeleted);
    }
}
