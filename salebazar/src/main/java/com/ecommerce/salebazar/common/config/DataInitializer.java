package com.ecommerce.salebazar.common.config;

import com.ecommerce.salebazar.user.entity.Role;
import com.ecommerce.salebazar.user.entity.User;
import com.ecommerce.salebazar.user.enums.RoleName;
import com.ecommerce.salebazar.user.repository.RoleRepository;
import com.ecommerce.salebazar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createRoleIfNotExists(RoleName.ROLE_ADMIN);
        createRoleIfNotExists(RoleName.ROLE_VENDOR);
        createRoleIfNotExists(RoleName.ROLE_CUSTOMER);

        if (userRepository.findByEmail("admin@salebazar.com").isPresent()) {
            return;
        }

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found"));

        User admin = User.builder()
                .email("admin@salebazar.com")
                .password(passwordEncoder.encode("Admin@123"))
                .enabled(true)
                .roles(Set.of(adminRole))
                .build();

        userRepository.save(admin);

        System.out.println("Default admin created: admin@salebazar.com / Admin@123");
    }

    private void createRoleIfNotExists(RoleName roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return roleRepository.save(role);
                });
    }
}
