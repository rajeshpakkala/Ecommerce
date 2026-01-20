package com.ecommerce.salebazar.auth.controller;

import com.ecommerce.salebazar.auth.dto.AdminDashboardResponse;
import com.ecommerce.salebazar.auth.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salebazar/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/home")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                dashboardService.getAdminDashboard()
        );
    }
}


