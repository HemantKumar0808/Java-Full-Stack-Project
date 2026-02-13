package com.bms.bank_management_system.responseDto;

import com.bms.bank_management_system.enums.TransactionStatus;
import com.bms.bank_management_system.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private String transactionId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String message;
    private BigDecimal newBalance;
}