package com.bms.bank_management_system.service;

import com.bms.bank_management_system.requestDto.TransactionRequest;
import com.bms.bank_management_system.responseDto.TransactionResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

public interface TransactionService {
    TransactionResponse deposit(TransactionRequest request);

    TransactionResponse withdraw(TransactionRequest request);

    TransactionResponse transfer(TransactionRequest request);
}
