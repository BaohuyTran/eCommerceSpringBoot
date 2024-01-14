/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.eCommerce.repository;

import com.example.eCommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
