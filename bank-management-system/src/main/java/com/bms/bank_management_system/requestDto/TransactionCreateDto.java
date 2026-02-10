package com.bms.bank_management_system.requestDto;

import com.bms.bank_management_system.enums.TransactionStatus;
import com.bms.bank_management_system.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionCreateDto {

    private String transactionId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String remarks;
}
