package com.ecommerce.salebazar.user.repository;

import com.ecommerce.salebazar.user.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    @Query("""
        SELECT COUNT(u)
        FROM User u
        JOIN u.roles r
        WHERE r.name = :role
    """)
    long countByRole(@Param("role") String role);

    @Query("""
        SELECT COUNT(u)
        FROM User u
        JOIN u.roles r
        WHERE r.name = 'ROLE_VENDOR'
        AND u.enabled = false
    """)
    long countPendingVendors();
}
