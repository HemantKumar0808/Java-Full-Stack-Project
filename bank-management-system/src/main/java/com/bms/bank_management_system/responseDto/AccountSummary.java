package com.bms.bank_management_system.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSummary {
    private String accountNo;
    private String accountType;
    private BigDecimal balance;
    private String status;
}