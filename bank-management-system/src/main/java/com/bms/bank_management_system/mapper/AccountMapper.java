package com.bms.bank_management_system.mapper;

import com.bms.bank_management_system.entity.Account;
import com.bms.bank_management_system.requestDto.AccountCreateRequest;
import com.bms.bank_management_system.responseDto.AccountResponse;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {

    // Request → Entity
    public Account toEntity(AccountCreateRequest request) {
        Account account = new Account();
        account.setAccountType(request.getAccountType());
//        account.setBalance(BigDecimal.ZERO);
        return account;
    }

    // Entity → Response
    public AccountResponse toResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setAccountNo(account.getAccountNo());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setAccountStatus(account.getAccountStatus());
        response.setCreatedAt(account.getCreatedAt());

        // Customer info show better practice
        if (account.getCustomer() != null) {
            response.setCustomerName(account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName());
            response.setCustomerId(account.getCustomer().getCustomerId());
        }

        return response;
    }
}
