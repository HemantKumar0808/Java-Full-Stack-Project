package com.bms.bank_management_system.service;

import com.bms.bank_management_system.responseDto.DashboardResponse;

public interface DashboardService {
     DashboardResponse getDashboard(String customerId);
}
