package com.ecommerce.salebazar.auth.controller;

import com.ecommerce.salebazar.auth.dto.AdminDashboardResponse;
import com.ecommerce.salebazar.auth.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salebazar/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class AdminDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/home")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {

        log.info("AUTH = {}",
                SecurityContextHolder.getContext().getAuthentication());

        log.info("AUTHORITIES = {}",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity.ok(
                dashboardService.getAdminDashboard()
        );
    }
}


