/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.controller;

import com.example.eCommerce.global.GlobalData;
import com.example.eCommerce.service.CategoryService;
import com.example.eCommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    
//    @GetMapping({"/"})
//    public String home(Model model) {
//        model.addAttribute("cartCount", GlobalData.cart.size());
//        return "shop";
//    }
    
    @GetMapping({"/", "/shop"})
    public String shop(Model model, @RequestParam(name = "sort", required = false) String sort) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts(sort));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }
    
    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProductByCategoryId(id));
        return "shop";
    }
    
    @GetMapping("/shop/viewProduct/{id}")
    public String viewProduct(Model model, @PathVariable long id) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        
        return "viewProduct";
    }
    
    @GetMapping("/shop/search")
    public String shopByKeyword(Model model, @RequestParam(required = false) String keyword, @RequestParam(name = "sort", required = false) String sort) {
        if (keyword == null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("products", productService.getAllProducts(sort));
            model.addAttribute("cartCount", GlobalData.cart.size());
        } else {
            model.addAttribute("products", productService.findProductByKeyword(keyword));
            model.addAttribute("cartCount", GlobalData.cart.size());
            model.addAttribute("categories", categoryService.getAllCategories());
        }              
        return "shop";
    }
}
