package com.ecommerce.salebazar.auth.service;

import com.ecommerce.salebazar.auth.dto.*;
import com.ecommerce.salebazar.auth.entity.BlacklistedToken;
import com.ecommerce.salebazar.auth.repository.BlacklistedTokenRepository;
import com.ecommerce.salebazar.common.config.JwtUtil;
import com.ecommerce.salebazar.common.dto.AuthResponseDTO;
import com.ecommerce.salebazar.exception.*;
import com.ecommerce.salebazar.user.entity.*;
import com.ecommerce.salebazar.user.enums.RoleName;
import com.ecommerce.salebazar.user.repository.*;
import com.ecommerce.salebazar.vendor.entity.Vendor;
import com.ecommerce.salebazar.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VendorRepository vendorRepository;
    private final JwtUtil jwtUtil;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
//    private final EmailService emailService;
//    private final VerificationTokenService tokenService;

    public AuthResponseDTO<RegisterResponseDTO> registerCustomer(
            RegisterRequestDTO request) {

        validateEmailNotExists(request.getEmail());

        Role role = getRole(RoleName.ROLE_CUSTOMER);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
//            sendVerification(user);

        return AuthResponseDTO.<RegisterResponseDTO>builder()
                .status(true)
                .responseCode(201)
                .message("Customer registered successfully. Verify email.")
                .data(RegisterResponseDTO.builder()
                        .email(user.getEmail())
                        .role("CUSTOMER")
                        .verificationRequired(true)
                        .build())
                .build();
    }

    public AuthResponseDTO<RegisterResponseDTO> registerVendor(
            RegisterRequestDTO request) {

        log.info("➡️ Vendor registration started");

        if (request == null) {
            log.error("❌ Vendor register request is NULL");
            throw new IllegalArgumentException("Request body is missing");
        }

        log.info("➡️ Vendor email received: {}", request.getEmail());

        validateEmailNotExists(request.getEmail());
        log.info("✅ Email not registered previously");

        Role role = getRole(RoleName.ROLE_VENDOR);
        log.info("✅ Vendor role fetched: {}", role.getName());

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
        log.info("✅ User entity saved with ID: {}", user.getId());

        Vendor vendor = Vendor.builder()
                .businessName(request.getBusinessName())
                .approved(false)
                .user(user)
                .build();

        vendorRepository.save(vendor);
        log.info("✅ Vendor entity saved for business: {}", request.getBusinessName());

        log.info("🎉 Vendor registration completed successfully for {}", user.getEmail());

        return AuthResponseDTO.<RegisterResponseDTO>builder()
                .status(true)
                .responseCode(201)
                .message("Vendor registered. Await admin approval & email verification.")
                .data(RegisterResponseDTO.builder()
                        .email(user.getEmail())
                        .role("VENDOR")
                        .verificationRequired(true)
                        .build())
                .build();
    }

    public AuthResponseDTO<RegisterResponseDTO> registerAdmin(
            RegisterRequestDTO request) {

        validateEmailNotExists(request.getEmail());

        Role role = getRole(RoleName.ROLE_ADMIN);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(role))
                .build();

        userRepository.save(user);

        return AuthResponseDTO.<RegisterResponseDTO>builder()
                .status(true)
                .responseCode(201)
                .message("Admin registered successfully.")
                .data(RegisterResponseDTO.builder()
                        .email(user.getEmail())
                        .role("ADMIN")
                        .verificationRequired(false)
                        .build())
                .build();
    }

    private void validateEmailNotExists(String email) {

        log.info(" Checking if email already exists: {}", email);

        if (userRepository.findByEmail(email).isPresent()) {
            log.error(" Email already exists in DB: {}", email);
            throw new ConflictException("Email already registered");
        }

        log.info(" Email does not exist: {}", email);

}

    private Role getRole(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() ->
                        new RuntimeException("Role not found: " + roleName));
    }

//        private void sendVerification(User user) {
//            String token = tokenService.createToken(user);
//            emailService.sendVerificationEmail(user.getEmail(), token);
//        }


    public AuthResponseDTO<LoginResponseDTO> login(LoginRequestDTO request) {

        log.info(" ENTERED AuthService.login()");

        if (request == null) {
            log.error("Request body is NULL");
            throw new UnauthorizedException("Request body is missing");
        }

        log.info("Email: {}", request.getEmail());

        if (isBlank(request.getEmail())) {
            log.error(" Email is blank");
            throw new UnauthorizedException("Email is required");
        }

        if (isBlank(request.getPassword())) {
            log.error(" Password is blank");
            throw new UnauthorizedException("Password is required");
        }

        log.info(" Fetching user from DB");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error(" User not found for email {}", request.getEmail());
                    return new UnauthorizedException("Invalid email or password");
                });

        log.info("User found: {}", user.getEmail());

//        if (!user.isEnabled()) {
//            log.error("User account not enabled");
//            throw new UnauthorizedException("Account not activated");
//        }

        log.info("Checking password");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error(" Password mismatch");
            throw new UnauthorizedException("Invalid email or password");
        }
        log.info("Password matched");
        log.info(" Generating JWT token");
        String token = jwtUtil.generateToken(user);

        String role = user.getRoles()
                .iterator()
                .next()
                .getName()
                .name();

        log.info("Login successful for {} with role {}", user.getEmail(), role);

        return AuthResponseDTO.<LoginResponseDTO>builder()
                .status(true)
                .responseCode(200)
                .message("Login successful")
                .data(LoginResponseDTO.builder()
                        .email(user.getEmail())
                        .role(role)
                        .token(token)
                        .build())
                .build();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public AuthResponseDTO<LogoutResponseDTO> logout(
            String authHeader,
            LogoutRequestDTO request) {

        log.info("➡️ Processing logout");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authorization token missing");
        }

        String token = authHeader.substring(7);

        if (blacklistedTokenRepository.existsByToken(token)) {
            throw new ConflictException("Token already logged out");
        }

        Date expiry = jwtUtil.extractExpiration(token);

        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .expiresAt(expiry.toInstant())
                .build();

        blacklistedTokenRepository.save(blacklistedToken);

        log.info("✅ Token blacklisted successfully");

        return AuthResponseDTO.<LogoutResponseDTO>builder()
                .status(true)
                .responseCode(200)
                .message("Logout successful")
                .data(LogoutResponseDTO.builder()
                        .loggedOut(true)
                        .build())
                .build();
    }


}

