package com.bms.bank_management_system.entity;

import com.bms.bank_management_system.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer_kyc_info")
@Data
public class CustomerKycInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Provide by Customer/User
    @Column(name = "pan_number", nullable = false, unique = true,updatable = false)
    private String panNumber;

    @Column(name = "aadhar_number", nullable = false, unique = true,updatable = false , length = 12)
    private String aadharNumber;

    @Column(name = "education", nullable = false)
    private String education;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    // Relations
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;
}