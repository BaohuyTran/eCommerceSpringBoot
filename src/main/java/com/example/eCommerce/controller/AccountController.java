/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.controller;

import com.example.eCommerce.model.User;
import com.example.eCommerce.model.UserAuthentication;
import com.example.eCommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author User
 */
@Controller
public class AccountController {
    @Autowired
    private UserService userService;
     
    @GetMapping("/account")
    public String getUserAccountForm(
        @AuthenticationPrincipal UserAuthentication userDetails,
        Model model
    ) {
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail).get();
         
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Account Details");
         
        return "account";
    }
}
