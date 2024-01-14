/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.global;

import com.example.eCommerce.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class GlobalData {
    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }
}
