package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;
import com.deepintent.auction.repository.ProductRepository;
import com.deepintent.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .address(productDto.getAddress())
                .price(productDto.getPrice())
                .build();
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        //TODO add validation
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .address(productDto.getAddress())
                .price(productDto.getPrice())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProduct(String id) {
        productRepository.deleteById(id);
        return true;
    }

}
