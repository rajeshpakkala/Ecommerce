package com.ecommerce.salebazar.auth.controller;

import com.ecommerce.salebazar.auth.dto.*;
import com.ecommerce.salebazar.auth.service.AuthService;
import com.ecommerce.salebazar.common.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;//

@RestController
@RequestMapping("salebazar/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponseDTO<RegisterResponseDTO>> registerCustomer(
         @RequestBody RegisterRequestDTO request) {

        return ResponseEntity
                .status(201)
                .body(authService.registerCustomer(request));
    }

    @PostMapping("/register/vendor")
    public ResponseEntity<AuthResponseDTO<RegisterResponseDTO>> registerVendor(
          @RequestBody RegisterRequestDTO request) {

        return ResponseEntity
                .status(201)
                .body(authService.registerVendor(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO request) {

        log.info(" LOGIN API HIT");
        log.info(" Request received: email={}",
                request != null ? request.getEmail() : "NULL");

        return ResponseEntity.ok(authService.login(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponseDTO<RegisterResponseDTO>> registerAdmin(
        @RequestBody RegisterRequestDTO request) {
        return ResponseEntity
                .status(201)
                .body(authService.registerAdmin(request));
    }
}
