package com.bms.bank_management_system.service.impl;

import com.bms.bank_management_system.entity.Account;
import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.entity.Transaction;
import com.bms.bank_management_system.enums.AccountStatus;
import com.bms.bank_management_system.enums.TransactionStatus;
import com.bms.bank_management_system.enums.TransactionType;
import com.bms.bank_management_system.exception.ResourceNotFoundException;
import com.bms.bank_management_system.mapper.AccountMapper;
import com.bms.bank_management_system.repository.AccountRepository;
import com.bms.bank_management_system.repository.CustomerRepository;
import com.bms.bank_management_system.repository.TransactionRepository;
import com.bms.bank_management_system.requestDto.AccountCreateRequest;
import com.bms.bank_management_system.responseDto.AccountResponse;
import com.bms.bank_management_system.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final BigDecimal NON_KYC_LIMIT = new BigDecimal("1000");

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(String customerId,
                                         AccountCreateRequest request) {

        //  Get authenticated customer
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with ID: " + customerId
                        ));

        //  Duplicate account type check
        boolean alreadyExists = customer.getAccounts().stream()
                .anyMatch(acc ->
                        acc.getAccountType() == request.getAccountType());

        if (alreadyExists) {
            throw new IllegalStateException(
                    "You already have a "
                            + request.getAccountType()
                            + " account"
            );
        }

        //  KYC validation for initial deposit
        boolean kycCompleted = customer.getKycInfo() != null;

        BigDecimal deposit = Optional.ofNullable(
                request.getInitialDeposit()
        ).orElse(BigDecimal.ZERO);

        if (!kycCompleted
                && deposit.compareTo(NON_KYC_LIMIT) > 0) {
            throw new IllegalArgumentException(
                    "Without KYC, initial deposit cannot exceed ₹1000"
            );
        }

        // Create account entity
        Account account = new Account();
        account.setAccountNo(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);

        account.setAccountStatus(kycCompleted ? AccountStatus.ACTIVE : AccountStatus.PENDING_KYC);
        account.setCustomer(customer);
        account.setCreatedAt(LocalDateTime.now());

        // Handle initial deposit
        if (deposit.compareTo(BigDecimal.ZERO) > 0) {

            account.setBalance(deposit);

            Transaction transaction = new Transaction();
            transaction.setTransactionId(generateTransactionId());
            transaction.setAmount(deposit);
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transaction.setTransactionDateTime(LocalDateTime.now());
            transaction.setRemarks("Initial deposit");
            transaction.setToAccount(account);

            account.getIncomingTransactions().add(transaction);
        }

        //  Save account (Cascade should save transaction)
        Account savedAccount = accountRepository.save(account);

        //  Prepare response
        AccountResponse response = accountMapper.toResponse(savedAccount);
        response.setMessage(kycCompleted ? "Account successfully created with full access (KYC already completed)!" : "Account created with limited access. Please complete KYC to remove limits.");
        return response;
    }

    private String generateAccountNumber() {
        return "ACC" +
                (1000000000L
                        + new Random().nextLong(9000000000L));
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}
