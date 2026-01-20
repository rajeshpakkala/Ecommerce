package com.ecommerce.salebazar.auth.dto;

import lombok.*;

@Builder
@Data
public class RegisterResponseDTO {
    private String email;
    private String role;
    private boolean verificationRequired;
}

