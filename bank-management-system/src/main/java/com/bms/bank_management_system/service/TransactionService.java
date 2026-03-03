package com.bms.bank_management_system.service;

import com.bms.bank_management_system.requestDto.TransactionRequest;
import com.bms.bank_management_system.responseDto.TransactionResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface TransactionService {
    TransactionResponse deposit(String customerId, TransactionRequest request);

    TransactionResponse withdraw(String customerId, TransactionRequest request);

    TransactionResponse transfer(String customerId, TransactionRequest request);

    List<TransactionResponse> getTransactionHistory(String accountNo, String customerId);
}
