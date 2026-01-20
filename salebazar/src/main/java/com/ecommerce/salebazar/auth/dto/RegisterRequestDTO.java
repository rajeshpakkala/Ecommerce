package com.ecommerce.salebazar.auth.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String email;

    private String password;

    // For vendor
    private String businessName;
}

