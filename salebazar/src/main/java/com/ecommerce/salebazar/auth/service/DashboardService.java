package com.ecommerce.salebazar.auth.service;

import com.ecommerce.salebazar.auth.dto.AdminDashboardResponse;
import com.ecommerce.salebazar.user.enums.RoleName;
import com.ecommerce.salebazar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
//    private final OrderRepository orderRepository;

    public AdminDashboardResponse getAdminDashboard() {

        return AdminDashboardResponse.builder()

                // USER METRICS
                .totalUsers(userRepository.count())
//                .totalCustomers(userRepository.countByRole("ROLE_CUSTOMER"))
//                .totalVendors(userRepository.countByRole("ROLE_VENDOR"))
//                .pendingVendorApprovals(
//                        userRepository.countPendingVendors()
//                )

//                // ORDER METRICS
//                .totalOrders(orderRepository.count())
//                .pendingOrders(orderRepository.countByStatus("PENDING"))
//                .completedOrders(orderRepository.countByStatus("COMPLETED"))
//                .cancelledOrders(orderRepository.countByStatus("CANCELLED"))
//
//                // REVENUE METRICS
//                .totalRevenue(orderRepository.totalRevenue())
//                .todayRevenue(orderRepository.todayRevenue())
//                .monthlyRevenue(orderRepository.monthlyRevenue())
//
//                // PRODUCT METRICS
//                .totalProducts(productRepository.count())
//                .outOfStockProducts(
//                        productRepository.countOutOfStock()
//                )

                // SYSTEM / HEALTH
                .activeSessions(0L)        // optional (Redis / sessions)
//                .failedPayments(
//                        orderRepository.failedPayments()
//                )

                .build();
    }
}

