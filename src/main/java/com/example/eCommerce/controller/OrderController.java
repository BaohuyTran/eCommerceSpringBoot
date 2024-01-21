/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.controller;

import com.example.eCommerce.dto.OrderDTO;
import com.example.eCommerce.global.GlobalData;
import com.example.eCommerce.model.Order;
import com.example.eCommerce.model.Product;
import com.example.eCommerce.model.User;
import com.example.eCommerce.model.UserAuthentication;
import com.example.eCommerce.service.OrderService;
import com.example.eCommerce.service.OrderStatusService;
import com.example.eCommerce.service.ProductService;
import com.example.eCommerce.service.UserAuthenticationService;
import com.example.eCommerce.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author User
 */
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    UserAuthenticationService userAuthenticationService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderStatusService orderStatusService;
        
    @GetMapping("/checkout")
    public String checkout(Model model) {
        double total = GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        if (total == 0) {
            return "redirect:/error";
        }
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("total", total);
        
        return "checkout";
    }
    
    @PostMapping("/checkout/pay")
    public String addOrder(@ModelAttribute("orderDTO")OrderDTO orderDTO, @AuthenticationPrincipal UserAuthentication userDetails) throws IOException {
        Order order = new Order();

        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail).get();

        List<Long> productIds = GlobalData.cart.stream().map(Product::getId).collect(Collectors.toList());
        List<Product> products = productIds.stream()
                .map(productId -> productService.getProductById(productId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());        
        double total = GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        
        order.setId(orderDTO.getOrderId());
        order.setUser(user);
        order.setProducts(products);
        order.setStatus(orderStatusService.getStatusById(1).get());
        order.setPrice(total);
        order.setAddress(orderDTO.getAddress());
        order.setPostcode(orderDTO.getPostcode());
        order.setPhone(orderDTO.getPhone());
        order.setDescription(orderDTO.getDescription());
        
        
        orderService.addOrder(order);
        GlobalData.cart.clear();
        return "redirect:/shop";
    }
    
    @GetMapping("/my-orders")
    public String getUserOrders (@ModelAttribute("orderDTO")OrderDTO orderDTO, @AuthenticationPrincipal UserAuthentication userDetails, Model model) throws IOException {
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail).get();
        
        List<Order> orders = orderService.getAllOrderByUserId(user.getId());
        
//        System.out.print(orders);
        
        model.addAttribute("orders", orders);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "my-orders";
    }
}
