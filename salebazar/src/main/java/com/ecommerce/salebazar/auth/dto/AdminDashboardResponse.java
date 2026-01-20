package com.ecommerce.salebazar.auth.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class AdminDashboardResponse {
    // USER METRICS
    private long totalUsers;
    private long totalCustomers;
    private long totalVendors;
    private long pendingVendorApprovals;

    // ORDER METRICS
    private long totalOrders;
    private long pendingOrders;
    private long completedOrders;
    private long cancelledOrders;

    // REVENUE METRICS
    private BigDecimal totalRevenue;
    private BigDecimal todayRevenue;
    private BigDecimal monthlyRevenue;

    // PRODUCT METRICS
    private long totalProducts;
    private long outOfStockProducts;

    // SYSTEM HEALTH (OPTIONAL BUT REAL-WORLD)
    private long activeSessions;
    private long failedPayments;
}
