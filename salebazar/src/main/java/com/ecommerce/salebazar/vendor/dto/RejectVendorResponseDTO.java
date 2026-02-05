package com.ecommerce.salebazar.vendor.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RejectVendorResponseDTO {

    private Long vendorId;
    private boolean approved;
    private String message;
}

