package com.ecommerce.salebazar.vendor.controller;


import com.ecommerce.salebazar.common.dto.*;
import com.ecommerce.salebazar.vendor.dto.*;
import com.ecommerce.salebazar.vendor.service.AdminVendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salebazar/vendors/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminVendorController {

    private final AdminVendorService adminVendorService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<
            AuthResponseDTO<PagedResponseDTO<PendingVendorDTO>>> getPendingVendors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("hitting");
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{vendorId}/reject")
    public ResponseEntity<AuthResponseDTO<RejectVendorResponseDTO>>
    rejectVendor(@PathVariable Long vendorId) {

        return ResponseEntity.ok(
                adminVendorService.rejectVendor(vendorId)
        );
    }

}
