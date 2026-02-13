package com.bms.bank_management_system.enums;

public enum AccountStatus {
    PENDING_KYC,  // KYC pending – limited functionality
    ACTIVE,  // Full access
    BLOCKED,
    CLOSED,
    FREEZE
}
