package com.bms.bank_management_system.entity;

import com.bms.bank_management_system.enums.AccountStatus;
import com.bms.bank_management_system.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Business Logic
    @Column(name = "account_no", unique = true, nullable = false, updatable = false, length = 20)
    private String accountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, columnDefinition = "varchar(30) default 'PENDING_KYC'")
    private AccountStatus accountStatus = AccountStatus.PENDING_KYC;

    // Provide by Customer/User
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "balance", nullable = false, columnDefinition = "decimal(15,2) default 0.00")
    private BigDecimal balance = BigDecimal.ZERO;


    // JPA Generate
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("from-account-transactions")
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("to-account-transactions")
    private List<Transaction> incomingTransactions = new ArrayList<>();

}