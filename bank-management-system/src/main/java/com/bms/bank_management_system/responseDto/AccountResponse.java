package com.bms.bank_management_system.responseDto;

import com.bms.bank_management_system.enums.AccountStatus;
import com.bms.bank_management_system.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private String accountNo;
    private AccountType accountType;
    private BigDecimal balance;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;

    // Optional: customer basic info
    private String customerName;
    private String customerId;

    // Custom message
    private String message;
}
