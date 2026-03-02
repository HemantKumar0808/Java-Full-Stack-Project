package com.bms.bank_management_system.controller;

import com.bms.bank_management_system.responseDto.DashboardResponse;
import com.bms.bank_management_system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/customer")
    public ResponseEntity<DashboardResponse> getDashboard(Authentication authentication) {
        String customerId = authentication.getName();
        DashboardResponse response = dashboardService.getDashboard(customerId);
        return ResponseEntity.ok(response);
    }

}
