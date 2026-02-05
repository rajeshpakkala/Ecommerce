package com.ecommerce.salebazar.auth.controller;

import com.ecommerce.salebazar.auth.dto.*;
import com.ecommerce.salebazar.auth.service.AuthService;
import com.ecommerce.salebazar.common.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;//

@RestController
@RequestMapping("/salebazar/auth")
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
        log.info("AUTH = {}",
                SecurityContextHolder.getContext().getAuthentication());

        log.info("AUTHORITIES = {}",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<AuthResponseDTO<LogoutResponseDTO>> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) LogoutRequestDTO request) {

        log.info(" LOGOUT API HIT");

        return ResponseEntity.ok(authService.logout(authHeader, request));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponseDTO<RegisterResponseDTO>> registerAdmin(
        @RequestBody RegisterRequestDTO request) {
        log.info("AUTH = {}",
                SecurityContextHolder.getContext().getAuthentication());

        log.info("AUTHORITIES = {}",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return ResponseEntity
                .status(201)
                .body(authService.registerAdmin(request));
    }
}
