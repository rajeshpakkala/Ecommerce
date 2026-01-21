package com.ecommerce.salebazar.auth.dto;

import lombok.Data;

@Data
public class LogoutRequestDTO {

    // Optional: useful if you plan token blacklist
    private String token;
}

