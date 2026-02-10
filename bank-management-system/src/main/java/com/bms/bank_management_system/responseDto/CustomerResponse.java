package com.bms.bank_management_system.responseDto;

import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.enums.CustomerStatus;
import com.bms.bank_management_system.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String email;
    private String phoneNo;
    private LocalDate dateOfBirth;
    private Gender gender;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Nested address
    private CustomerAddressResponse address;

    // Custom message
    private String message;

}
