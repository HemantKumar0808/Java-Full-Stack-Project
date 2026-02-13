package com.bms.bank_management_system.repository;

import com.bms.bank_management_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Basic finds (signup aur login ke liye)
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNo(String phoneNo);
    Optional<Customer> findByCustomerId(String customerId);

    // PAN uniqueness check
//    boolean existsByPanNumber(String panNumber);

    // Aadhaar uniqueness check
//    boolean existsByAadharNumber(String aadharNumber);


    // KYC uniqueness checks – sirf @Query wale (naming convention wale hata diye)
    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END " +
            "FROM CustomerKycInfo k WHERE k.panNumber = :pan")
    boolean existsByPanNumber(@Param("pan") String pan);

    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END " +
            "FROM CustomerKycInfo k WHERE k.aadharNumber = :aadhar")
    boolean existsByAadharNumber(@Param("aadhar") String aadhar);

    // Optional: agar ek call mein dono check karna ho
    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END " +
            "FROM CustomerKycInfo k WHERE k.panNumber = :pan OR k.aadharNumber = :aadhar")
    boolean existsByPanOrAadhar(@Param("pan") String pan, @Param("aadhar") String aadhar);
}


