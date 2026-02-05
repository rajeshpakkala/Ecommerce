package com.ecommerce.salebazar.vendor.service;


import com.ecommerce.salebazar.common.dto.*;
import com.ecommerce.salebazar.exception.NotFoundException;
import com.ecommerce.salebazar.vendor.dto.*;
import com.ecommerce.salebazar.vendor.entity.Vendor;
import com.ecommerce.salebazar.vendor.enums.VendorStatus;
import com.ecommerce.salebazar.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminVendorService {

    private final VendorRepository vendorRepository;

    public AuthResponseDTO<PagedResponseDTO<PendingVendorDTO>>
    getPendingVendors(int page, int size) {

        log.info("➡️ Fetching pending vendors | page={} size={}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Vendor> vendorPage =
                vendorRepository.findPendingVendors(pageable);

        List<PendingVendorDTO> content = vendorPage.getContent()
                .stream()
                .map(v -> PendingVendorDTO.builder()
                        .vendorId(v.getId())
                        .email(v.getUser().getEmail())
                        .businessName(v.getBusinessName())
                        .approved(v.isApproved())
                        .build())
                .toList();

        PagedResponseDTO<PendingVendorDTO> pagedResponse =
                PagedResponseDTO.<PendingVendorDTO>builder()
                        .content(content)
                        .page(vendorPage.getNumber())
                        .size(vendorPage.getSize())
                        .totalElements(vendorPage.getTotalElements())
                        .totalPages(vendorPage.getTotalPages())
                        .last(vendorPage.isLast())
                        .build();

        return AuthResponseDTO.<PagedResponseDTO<PendingVendorDTO>>builder()
                .status(true)
                .responseCode(200)
                .message("Pending vendors fetched successfully")
                .data(pagedResponse)
                .build();
    }

    public AuthResponseDTO<ApproveVendorResponseDTO> approveVendor(Long vendorId) {

        log.info("Approving vendor with ID: {}", vendorId);

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> {
                    log.error("Vendor not found: {}", vendorId);
                    return new NotFoundException("Vendor not found");
                });

        if (vendor.isApproved()) {
            log.warn("Vendor already approved: {}", vendorId);
        }

        vendor.setVendorStatus(VendorStatus.APPROVED);
        vendor.setRejectionReason(null);
        vendorRepository.save(vendor);

        log.info("Vendor approved successfully: {}", vendorId);

        return AuthResponseDTO.<ApproveVendorResponseDTO>builder()
                .status(true)
                .responseCode(200)
                .message("Vendor approved successfully")
                .data(ApproveVendorResponseDTO.builder()
                        .vendorId(vendor.getId())
                        .approved(true)
                        .message("Vendor account activated")
                        .build())
                .build();
    }

    public AuthResponseDTO<RejectVendorResponseDTO> rejectVendor(Long vendorId) {

        log.info("Rejecting vendor with ID: {}", vendorId);

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> {
                    log.error("Vendor not found: {}", vendorId);
                    return new NotFoundException("Vendor not found");
                });

        if (!vendor.isApproved()) {
            log.warn("Vendor already rejected or not approved: {}", vendorId);
        }

       vendor.setVendorStatus(VendorStatus.REJECTED);
        vendor.setRejectionReason("Documents not verified");
        vendorRepository.save(vendor);

        log.info("Vendor rejected successfully: {}", vendorId);

        return AuthResponseDTO.<RejectVendorResponseDTO>builder()
                .status(true)
                .responseCode(200)
                .message("Vendor rejected successfully")
                .data(RejectVendorResponseDTO.builder()
                        .vendorId(vendor.getId())
                        .approved(false)
                        .message("Vendor account rejected")
                        .build())
                .build();
    }

}

