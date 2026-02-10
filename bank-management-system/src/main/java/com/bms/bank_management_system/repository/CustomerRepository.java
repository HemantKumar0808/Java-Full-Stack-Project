package com.bms.bank_management_system.repository;

import com.bms.bank_management_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
//    // Basic unique checks (signup ke time use hoga)
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNo(String phoneNo);
//    Optional<Customer> findByCustomerId(String customerId);
//
//    // Agar customer ke saath address aur additional info bhi fetch karna ho (eager fetch)
//@Query("""
//    SELECT c
//      FROM Customer c
// LEFT JOIN FETCH c.customerAddress
// LEFT JOIN FETCH c.additionalInfo
//     WHERE c.id = :id
//    """)
//Optional<Customer> findByIdWithDetails(Long id);
//    Optional<Customer> findByIdWithDetails(Long id);
}
