package com.ecommerce.salebazar.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutResponseDTO {

    private boolean loggedOut;
}

