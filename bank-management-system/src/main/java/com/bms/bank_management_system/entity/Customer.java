package com.bms.bank_management_system.entity;

import com.bms.bank_management_system.enums.CustomerStatus;
import com.bms.bank_management_system.enums.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false, unique = true, updatable = false)
    private String customerId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_no", nullable = false, unique = true)
    private String phoneNo;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "status") //columnDefinition = "varchar(20) default 'ACTIVE'"
    private CustomerStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private CustomerAddress customerAddress;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private CustomerAdditionalInfo additionalInfo;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

    // getters, setters, constructors...


    public Customer() {
    }

    public Customer(String customerId, String email, String phoneNo, String firstName, String lastName, String fatherName, Gender gender, LocalDate dateOfBirth, CustomerStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, CustomerAddress customerAddress, CustomerAdditionalInfo additionalInfo, List<Account> accounts) {
        this.customerId = customerId;
        this.email = email;
        this.phoneNo = phoneNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerAddress = customerAddress;
        this.additionalInfo = additionalInfo;
        this.accounts = accounts;
    }

}