/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.service;

import com.example.eCommerce.model.User;
import com.example.eCommerce.repository.UserRepository;
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
public class UserService {
    @Autowired
    UserRepository userRepository;
    
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        
        return users;
    }
    
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        
        return userRepository.findAll(pageable);
    }
    
    public void addUser(User user) {
        userRepository.save(user);
    }
    
    public void removeUserById(int id) {
        userRepository.deleteById(id);
    }
    
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
