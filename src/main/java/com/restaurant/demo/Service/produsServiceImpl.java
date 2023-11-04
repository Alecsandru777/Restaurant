package com.restaurant.demo.Service;

import com.restaurant.demo.DAO.ProductRepository;
import com.restaurant.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class produsServiceImpl implements ProductService {

    private ProductRepository productRepository;
    @Autowired
    public produsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Integer id) {
        Optional<Product> result = productRepository.findById(id);

        Product theProduct = null;
        if (result.isPresent()){
            theProduct = result.get();
        }
        return theProduct;

    }
}
