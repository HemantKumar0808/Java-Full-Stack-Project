package com.bms.bank_management_system.service.impl;

import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.exception.ResourceNotFoundException;
import com.bms.bank_management_system.repository.CustomerRepository;
import com.bms.bank_management_system.responseDto.AccountSummary;
import com.bms.bank_management_system.responseDto.DashboardResponse;
import com.bms.bank_management_system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;

    @Override
    public DashboardResponse getDashboard(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<AccountSummary> accounts = customer.getAccounts().stream()
                .map(acc -> AccountSummary.builder()
                        .accountNo(acc.getAccountNo())
                        .accountType(acc.getAccountType().name())
                        .balance(acc.getBalance())
                        .status(acc.getAccountStatus().name())
                        .build())
                .toList();

        boolean kycCompleted = customer.getKycInfo() != null;

        return DashboardResponse.builder()
                .customerId(customer.getCustomerId())
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .email(customer.getEmail())
                .phoneNo(customer.getPhoneNo())
                .kycCompleted(kycCompleted)
                .accounts(accounts)
                .build();
    }

}
