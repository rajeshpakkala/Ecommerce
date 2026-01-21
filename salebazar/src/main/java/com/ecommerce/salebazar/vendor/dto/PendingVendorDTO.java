package com.ecommerce.salebazar.vendor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PendingVendorDTO {

    private Long vendorId;
    private String email;
    private String businessName;
    private boolean approved;
}
