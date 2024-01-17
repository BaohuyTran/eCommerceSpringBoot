/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.eCommerce.dto;

import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int roleId;
}
