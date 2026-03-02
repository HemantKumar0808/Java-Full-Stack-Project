package com.bms.bank_management_system.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponse {
    private String customerId;
    private String fullName;
    private String email;
    private String phoneNo;
    private boolean kycCompleted;
    private List<AccountSummary> accounts;
}