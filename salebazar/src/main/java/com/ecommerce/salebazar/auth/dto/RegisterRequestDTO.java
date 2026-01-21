package com.ecommerce.salebazar.auth.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private final String name;
    private final String mobileNumber;
    private final String email;
    private final String password;
    // For vendor
    private String businessName;
}

