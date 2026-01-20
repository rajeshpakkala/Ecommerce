package com.ecommerce.salebazar.auth.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;
    private String email;
    private String role;
}

