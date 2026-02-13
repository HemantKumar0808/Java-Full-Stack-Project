package com.bms.bank_management_system.repository;

import com.bms.bank_management_system.entity.Account;
import com.bms.bank_management_system.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

//    // Account number find
    Optional<Account> findByAccountNo(String accountNo);

//    // Customer ke saare accounts
//    List<Account> findByCustomerId(Long customerId);

//    // Active accounts only
//    List<Account> findByCustomerIdAndAccountStatus(Long customerId, AccountStatus accountStatus);
}
