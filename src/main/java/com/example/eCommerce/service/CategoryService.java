/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.Category;
import com.example.eCommerce.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    
    public void removeCategoryById(int id) {
        categoryRepository.deleteById(id);
    }
    
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
}
