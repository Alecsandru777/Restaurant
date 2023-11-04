package com.restaurant.demo.Service;

import com.restaurant.demo.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> findAll();
    public Product save(Product product);
    public Product findById(Integer id);
}
