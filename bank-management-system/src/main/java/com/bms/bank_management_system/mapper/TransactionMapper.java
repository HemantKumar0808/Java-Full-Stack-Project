package com.bms.bank_management_system.mapper;

import com.bms.bank_management_system.entity.Transaction;
import com.bms.bank_management_system.enums.TransactionStatus;
import com.bms.bank_management_system.enums.TransactionType;
import com.bms.bank_management_system.requestDto.TransactionRequest;
import com.bms.bank_management_system.responseDto.TransactionResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequest request, TransactionType type) {
        Transaction tx = new Transaction();
        tx.setAmount(request.getAmount());
        tx.setTransactionType(type);
        tx.setRemarks(request.getRemarks());
        tx.setTransactionStatus(TransactionStatus.SUCCESS);
        tx.setTransactionDateTime(LocalDateTime.now());
        tx.setTransactionId(generateTransactionId());
        return tx;
    }

    public TransactionResponse toResponse(Transaction tx, BigDecimal newBalance) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(tx.getTransactionId());
        response.setAmount(tx.getAmount());
        response.setTransactionType(tx.getTransactionType());
        response.setStatus(tx.getTransactionStatus());
        response.setTimestamp(tx.getTransactionDateTime());
        response.setNewBalance(newBalance);
        response.setMessage("Transaction successful");
        return response;
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }
}
