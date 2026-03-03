package com.bms.bank_management_system.service.impl;

import com.bms.bank_management_system.entity.Account;
import com.bms.bank_management_system.entity.Transaction;
import com.bms.bank_management_system.enums.TransactionType;
import com.bms.bank_management_system.exception.ResourceNotFoundException;
import com.bms.bank_management_system.exception.UnauthorizedAccessException;
import com.bms.bank_management_system.mapper.TransactionMapper;
import com.bms.bank_management_system.repository.AccountRepository;
import com.bms.bank_management_system.repository.TransactionRepository;
import com.bms.bank_management_system.requestDto.TransactionRequest;
import com.bms.bank_management_system.responseDto.TransactionResponse;
import com.bms.bank_management_system.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponse deposit(String customerId, TransactionRequest request) {
        return performTransaction(customerId, request, TransactionType.DEPOSIT);
    }

    @Override
    public TransactionResponse withdraw(String customerId, TransactionRequest request) {
        return performTransaction(customerId, request, TransactionType.WITHDRAW);
    }

    private TransactionResponse performTransaction(String customerId,TransactionRequest request, TransactionType type) {
        Account account = accountRepository.findByAccountNo(request.getAccountNo())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // 🔐 Ownership Check (VERY IMPORTANT)
        if (!account.getCustomer().getCustomerId().equals(customerId)) {
            throw new UnauthorizedAccessException("You are not allowed to access this account");
        }


        boolean kycDone = account.getCustomer().getKycInfo() != null;

        // KYC Limit check
        if(!kycDone && request.getAmount().compareTo(new BigDecimal("1000"))>0){
            throw new IllegalArgumentException("KYC pending. Transaction limit ₹1000. Complete KYC for higher amounts.");
        }

        // Check balance for Withdraw
        if(type == TransactionType.WITHDRAW){
            if(account.getBalance().compareTo(request.getAmount()) < 0){
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(request.getAmount()));
        }else { // Deposit
            account.setBalance(account.getBalance().add(request.getAmount()));
        }

        Transaction tx = transactionMapper.toEntity(request,type);

        if(type == TransactionType.DEPOSIT){
            tx.setToAccount(account);
            account.getIncomingTransactions().add(tx);
        } else if (type == TransactionType.WITHDRAW) {
            tx.setFromAccount(account);
            account.getOutgoingTransactions().add(tx);
        }

        transactionRepository.save(tx);
        accountRepository.save(account);

        return transactionMapper.toResponse(tx, account.getBalance());
    }


    @Override
    public TransactionResponse transfer(String customerId, TransactionRequest request) {
        if (request.getToAccountNo() == null) {
            throw new IllegalArgumentException("To account number is required for transfer");
        }

        Account from = accountRepository.findByAccountNo(request.getAccountNo())
                .orElseThrow(() -> new ResourceNotFoundException("From account not found"));

        // 🔐 Ownership Check (VERY IMPORTANT)
        if (!from.getCustomer().getCustomerId().equals(customerId)) {
            throw new UnauthorizedAccessException("You are not allowed to access this account");
        }

        Account to = accountRepository.findByAccountNo(request.getToAccountNo())
                .orElseThrow(() -> new ResourceNotFoundException("To account not found"));

        boolean kycDone = from.getCustomer().getKycInfo() != null;

        if (!kycDone && request.getAmount().compareTo(new BigDecimal("1000")) > 0) {
            throw new IllegalArgumentException("KYC pending. Transfer limit ₹1000.");
        }

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        Transaction tx = transactionMapper.toEntity(request, TransactionType.TRANSFER);
        tx.setFromAccount(from);
        tx.setToAccount(to);

        transactionRepository.save(tx);
        accountRepository.saveAll(List.of(from, to));

        return transactionMapper.toResponse(tx, from.getBalance());
    }

    @Override
    public List<TransactionResponse> getTransactionHistory(String accountNo, String customerId) {

            Account account = accountRepository.findByAccountNo(accountNo)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

            // 🔐 Ownership Check (VERY IMPORTANT)
            if (!account.getCustomer().getCustomerId().equals(customerId)) {
                throw new UnauthorizedAccessException("You are not allowed to access this account");
            }

        List<Transaction> outgoing = transactionRepository.findByFromAccountId(account.getId());
        List<Transaction> incoming = transactionRepository.findByToAccountId(account.getId());

        List<Transaction> all = new ArrayList<>();
        all.addAll(outgoing);
        all.addAll(incoming);
        all.sort((a, b) -> b.getTransactionDateTime().compareTo(a.getTransactionDateTime())); // Sort by date desc

        return all.stream()
                .map(tx -> transactionMapper.toResponse(tx, account.getBalance()))
                .collect(Collectors.toList());

    }
}
