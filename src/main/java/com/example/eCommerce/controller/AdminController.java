/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.controller;

import com.example.eCommerce.dto.ProductDTO;
import com.example.eCommerce.dto.UserDTO;
import com.example.eCommerce.model.Category;
import com.example.eCommerce.model.Order;
import com.example.eCommerce.model.Product;
import com.example.eCommerce.model.User;
import com.example.eCommerce.service.CategoryService;
import com.example.eCommerce.service.OrderService;
import com.example.eCommerce.service.RoleService;
import com.example.eCommerce.service.ProductService;
import com.example.eCommerce.service.UserService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @GetMapping("")
    public String adminHome() {
        return "adminHome";
    }
    
    //Category management
    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }    
    @GetMapping("/categories/add")
    public String getCategoryAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }    
    @PostMapping("/categories/add")
    public String postCategoryAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }    
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }    
    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }
    }
    
    //Product management
    @GetMapping("/products")
    public String getProducts(Model model) {
        return findProductPaginated(1, "name", "asc", model);
    }
    @GetMapping("/products/add")
    public String getProductAddForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productsAdd";
    }
    @PostMapping("/products/add")
    public String postProductAdd(
            @ModelAttribute("productDTO")ProductDTO productDTO, 
            @RequestParam("productImage")MultipartFile file,
            @RequestParam("imgName")String imgName
    ) throws IOException {
        Product product = new Product();
        
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        
        productService.addProduct(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/products/update/{id}")
    public String updateProduct(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setImageName(product.getImageName());
        
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("productDTO", productDTO);
        
        return "productsAdd";
    }
    
    @GetMapping("/products/page{pageNo}")
    public String findProductPaginated(
            @PathVariable (value = "pageNo") int pageNo, 
            @RequestParam("sortField") String sortField, 
            @RequestParam("sortDir") String sortDir, 
            Model model
    ) {
        int pageSize = 5;
        
        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Product> products = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
        model.addAttribute("products", products);
        
        return "products";
    }
    
    // Users management
    @GetMapping("/users")
    public String getUsers(Model model) {
        return findUserPaginated(1, "firstName", "asc", model);
    }
    
    @GetMapping("/users/page{pageNo}")
    public String findUserPaginated(
            @PathVariable (value = "pageNo") int pageNo, 
            @RequestParam("sortField") String sortField, 
            @RequestParam("sortDir") String sortDir, 
            Model model
    ) {
        int pageSize = 5;
        
        Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<User> users = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
        model.addAttribute("users", users);
        
        return "users";
    }
    @GetMapping("/users/add")
    public String getUserAddForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("roles", roleService.getAllRoles());
        return "usersAdd";
    }
    @PostMapping("/users/add")
    public String postUserAdd(@ModelAttribute("userDTO")UserDTO userDTO) {
        User user = new User();
        
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(roleService.getRoleById(userDTO.getRoleId()).get());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword())); //authenticate?

        userService.addUser(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/users/update/{id}")
    public String updateUser(@PathVariable int id, Model model) {
        User user = userService.getUserById(id).get();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setRoleId(user.getRole().getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword("");
        
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("userDTO", userDTO);
        
        return "usersAdd";
    }
    
    //Orders management
    @GetMapping("/orders")
    public String getOrders(Model model) {
        return findOrderPaginated(1, "user.firstName", "asc", model);
    }
    @GetMapping("/orders/page{pageNo}")
    public String findOrderPaginated(
            @PathVariable (value = "pageNo") int pageNo, 
            @RequestParam("sortField") String sortField, 
            @RequestParam("sortDir") String sortDir, 
            Model model
    ) {
        int pageSize = 5;
        
        Page<Order> page = orderService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Order> orders = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
        model.addAttribute("orders", orders);
        
        return "orders";
    }
}
