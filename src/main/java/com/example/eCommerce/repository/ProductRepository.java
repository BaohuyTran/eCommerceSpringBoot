/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.eCommerce.repository;

import com.example.eCommerce.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author User
 */
public interface ProductRepository extends JpaRepository<Product, Long>{

    public List<Product> findAllByCategory_Id(int id);
    
    @Query(value = "SELECT * FROM PRODUCT p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    public List<Product> findByKeyword(@Param("keyword") String keyword);
}
