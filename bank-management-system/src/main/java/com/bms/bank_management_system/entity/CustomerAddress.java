package com.bms.bank_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "customer_address")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_no", nullable = false, length = 50)
    private String houseNo;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "landmark", length = 50)
    private String landmark;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "pin_code", nullable = false, length = 6)
    private String pinCode;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    // getters, setters...
}