/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.OrderStatus;
import com.example.eCommerce.repository.OrderStatusRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class OrderStatusService {
    @Autowired
    OrderStatusRepository orderStatusRepository;
    
    public List<OrderStatus> getAllStatus() {
        return orderStatusRepository.findAll();
    }
    
    public Optional<OrderStatus> getStatusById(int id) {
        return orderStatusRepository.findById(id);
    }
}
