/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.Order;
import com.example.eCommerce.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        
        return orders;
    }
    
    public void addOrder(Order order) {
        orderRepository.save(order);
    }
    
    public List<Order> getAllOrderByStatusId(int id) {
        List<Order> orders = orderRepository.findAllByStatus_Id(id);
        
        return orders;
    }
}
