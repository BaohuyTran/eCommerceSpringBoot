/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.Product;
import com.example.eCommerce.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public void addProduct(Product product) {
        productRepository.save(product);
    }
    
    public void removeProductById(long id) {
        productRepository.deleteById(id);
    }
    
    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getAllProductByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);
    }
    
    public List<Product> findProductByKeyword(String keyword) {
        return productRepository.findByKeyword(keyword);
    }
}
