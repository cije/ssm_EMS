package com.ce.service;

import com.ce.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll(int page,int size);

    void save(Product product);
}
