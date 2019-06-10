package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.repository.ProductRepository;
import com.deepintent.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product productToCreate = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .address(productDto.getAddress())
                .price(productDto.getPrice())
                .build();
        return productRepository.save(productToCreate);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        Optional<Product> product = productRepository.findById(productDto.getId());
        if (product.isPresent()) {
            Product productToUpdate = Product.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .description(productDto.getDescription())
                    .address(productDto.getAddress())
                    .price(productDto.getPrice())
                    .build();
            return productRepository.save(productToUpdate);
        }
        throw new EntityNotFoundException("Product not found with given id : " + productDto.getId());
    }

    @Override
    public Boolean deleteProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Product not found with given id :" + id);
    }

}
