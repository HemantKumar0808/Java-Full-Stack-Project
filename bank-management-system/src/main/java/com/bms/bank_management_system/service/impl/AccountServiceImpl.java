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
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    private final AccountMapper accountMapper;


    private String generateAccountNumber() {
        // Simple example – real mein bank branch code + sequence use karna chahiye
        return "ACC" + (1000000000L + new Random().nextLong(9000000000L));
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }

    @Override
    public AccountResponse createAccount(AccountCreateRequest request) {

        // Customer ID send by user (CUST-1723456789123)
        String providedCustomerId = request.getCustomerId();

        // 1. Customer ID check (business ID se)
        Customer customer = customerRepository.findByCustomerId(providedCustomerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with customerId: " + providedCustomerId));

        // 2. Account type duplicate check
        boolean alreadyHasType = customer.getAccounts().stream()
                .anyMatch(acc -> acc.getAccountType() == request.getAccountType());

        if (alreadyHasType) {
            throw new IllegalStateException("You already have a " + request.getAccountType() + " account");
        }

        // 3. Create account
        Account account = accountMapper.toEntity(request);

        // 4. Business logic add
        account.setAccountNo(generateAccountNumber());

        // 5. Set customer in account
        account.setCustomer(customer);

        // 7. KYC status check
        boolean kycDone = customer.getKycInfo() != null;

        // 7. Set Account status behalf of KYC info
        if (kycDone) {
            account.setAccountStatus(AccountStatus.ACTIVE);
        } else {
            account.setAccountStatus(AccountStatus.PENDING_KYC);
        }

        // 8. Initial deposit logic
        BigDecimal deposit = request.getInitialDeposit();

        if (deposit != null && deposit.compareTo(BigDecimal.ZERO) > 0) {

            if (!kycDone) {
                if (deposit.compareTo(new BigDecimal("1000")) > 0) {
                    throw new IllegalArgumentException("Without KYC, initial deposit cannot exceed ₹1000.");
                }
            }

            // 8.1. Set initial amount in account
            account.setBalance(account.getBalance().add(deposit));

            // 8.2. Need to make initial deposit TX
            Transaction tx = new Transaction();
            tx.setTransactionId(generateTransactionId());
            tx.setAmount(deposit);
            tx.setTransactionType(TransactionType.DEPOSIT);
            tx.setTransactionStatus(TransactionStatus.SUCCESS);
            tx.setTransactionDateTime(LocalDateTime.now());
            tx.setRemarks("Initial deposit");

            // # Many to one - set
            tx.setToAccount(account);

            // # One to many - set
            account.getIncomingTransactions().add(tx);

            // 8.3. Save transaction in db
            transactionRepository.save(tx);
        }


        // 9. Save account
        Account savedAccount = accountRepository.save(account);

        // 10. Prepare response
        AccountResponse response = accountMapper.toResponse(savedAccount);
        response.setMessage(kycDone
                ? "Account successfully created with full access (KYC already completed)!"
                : "Account created with limited access. Please complete KYC to remove limits.");

        return response;
    }


}
