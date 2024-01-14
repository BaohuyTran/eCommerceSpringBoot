/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.controller;

import com.example.eCommerce.global.GlobalData;
import com.example.eCommerce.model.Role;
import com.example.eCommerce.model.User;
import com.example.eCommerce.repository.RoleRepository;
import com.example.eCommerce.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author User
 */
@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    
    @GetMapping("/login")
    public String login() {
        GlobalData.cart.clear();
        return "login";
    }
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }
    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        
        userRepository.save(user);
        request.login(user.getEmail(), password);
        
        return "redirect:/";
    } 
}
