package com.bms.bank_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "customer_additional_info")
public class CustomerAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pan_number", nullable = false, unique = true)
    private String panNumber;

    @Column(name = "aadhar_number", nullable = false, unique = true, length = 12)
    private String aadharNumber;

    @Column(name = "education", nullable = false)
    private String education;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "existing_account", columnDefinition = "boolean default false")
    private Boolean hasExistingAccount = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    // getters, setters...
}