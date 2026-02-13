package com.bms.bank_management_system.repository;

import com.bms.bank_management_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    // Ek account ke incoming transactions (deposits + received transfers)
    List<Transaction> findByToAccountId(Long accountId);

    // Ek account ke outgoing transactions (withdrawals + sent transfers)
    List<Transaction> findByFromAccountId(Long accountId);

    // Mini statement: last 10 transactions (sent or received)
    List<Transaction> findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateTimeDesc(
            Long accountId1, Long accountId2);

    // Date range ke transactions (statement ke liye)
    List<Transaction> findByFromAccountIdAndTransactionDateTimeBetween(
            Long accountId, LocalDateTime start, LocalDateTime end);

    List<Transaction> findByToAccountIdAndTransactionDateTimeBetween(
            Long accountId, LocalDateTime start, LocalDateTime end);

}
