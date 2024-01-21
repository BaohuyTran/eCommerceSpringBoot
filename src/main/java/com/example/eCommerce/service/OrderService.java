/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.Order;
import com.example.eCommerce.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
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
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        
        return orders;
    }
    
    public Page<Order> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        
        return orderRepository.findAll(pageable);
    }
    
    public void addOrder(Order order) {
        orderRepository.save(order);
    }
    public List<Order> getAllOrderByUserId(int id) {
        List<Order> orders = orderRepository.findAllByUser_Id(id);
        
        return orders;
    }
    public List<Order> getAllOrderByStatusId(int id) {
        List<Order> orders = orderRepository.findAllByStatus_Id(id);
        
        return orders;
    }
    
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }
}
