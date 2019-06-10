package com.deepintent.auction.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;
import com.deepintent.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private ProductService productService;

    @Autowired
    public ProductResolver(ProductService productService) {
        this.productService = productService;
    }

    public Product createProduct(ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Product updateProduct(ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    public Boolean deleteProduct(String id) {
        return productService.deleteProduct(id);
    }
}
