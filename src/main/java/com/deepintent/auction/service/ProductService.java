package com.deepintent.auction.service;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    List<Product> getAllProducts();

    Product updateProduct(ProductDto productDto);

    Boolean deleteProduct(String id);

}
