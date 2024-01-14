/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.Product;
import com.example.eCommerce.repository.ProductRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    
    public List<Product> getAllProducts(String sort) {
        List<Product> products = productRepository.findAll();
        
        if ("priceAsc".equals(sort)) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        } else if ("priceDesc".equals(sort)) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        } else if ("nameAsc".equals(sort)) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getName))
                    .collect(Collectors.toList());
        } else if ("nameDesc".equals(sort)) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getName).reversed())
                    .collect(Collectors.toList());
        }
        
        return products;
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
        List<Product> products = productRepository.findAllByCategory_Id(id);
        
        return products;
    }
    
    public List<Product> findProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByKeyword(keyword);
 
        return products;
    }
    
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        
        return productRepository.findAll(pageable);
    }
}
