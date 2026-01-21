package com.ecommerce.salebazar.vendor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApproveVendorResponseDTO {

    private Long vendorId;
    private boolean approved;
    private String message;
}

