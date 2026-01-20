package com.ecommerce.salebazar.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO<T> {

    private boolean status;
    private int responseCode;
    private String message;
    private T data;
}

