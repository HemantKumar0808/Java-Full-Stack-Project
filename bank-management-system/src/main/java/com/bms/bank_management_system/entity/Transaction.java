package com.bms.bank_management_system.entity;

import com.bms.bank_management_system.enums.TransactionStatus;
import com.bms.bank_management_system.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Provide by Customer/User
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "remarks", length = 500)
    private String remarks;

    // Business Logic
    @Column(name = "transaction_id", unique = true, nullable = false, updatable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false) // columnDefinition = "varchar(20) default 'SUCCESS'"
    private TransactionStatus TransactionStatus;


    @CreationTimestamp
    @Column(name = "transaction_date_time", updatable = false)
    private LocalDateTime transactionDateTime;

    // From account (for WITHDRAW & TRANSFER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    @JsonBackReference("from-account-transactions")
    private Account fromAccount;

    // To account (for DEPOSIT & TRANSFER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    @JsonBackReference("to-account-transactions")
    private Account toAccount;

}