package com.ecommerce.salebazar.vendor.controller;


import com.ecommerce.salebazar.common.dto.*;
import com.ecommerce.salebazar.vendor.dto.*;
import com.ecommerce.salebazar.vendor.service.AdminVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salebazar/admin/vendors")
@RequiredArgsConstructor
public class AdminVendorController {

    private final AdminVendorService adminVendorService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<
            AuthResponseDTO<PagedResponseDTO<PendingVendorDTO>>> getPendingVendors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                adminVendorService.getPendingVendors(page, size)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{vendorId}/approve")
    public ResponseEntity<AuthResponseDTO<ApproveVendorResponseDTO>>
    approveVendor(@PathVariable Long vendorId) {

        return ResponseEntity.ok(
                adminVendorService.approveVendor(vendorId)
        );
    }
}
