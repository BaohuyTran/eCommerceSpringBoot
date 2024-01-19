/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.repository;

import com.example.eCommerce.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    
}
