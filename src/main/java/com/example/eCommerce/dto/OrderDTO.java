/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class OrderDTO {
    private int orderId;
    private int userId;
    private List<Integer> productIds;
    private double price;
    private String address;
    private String postcode;
    private String phone;
    private String description;
    private int statusId;
}
