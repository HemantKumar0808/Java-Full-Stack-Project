package com.bms.bank_management_system.service.impl;

import com.bms.bank_management_system.requestDto.TransactionRequest;
import com.bms.bank_management_system.responseDto.TransactionResponse;
import com.bms.bank_management_system.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponse deposit(TransactionRequest request) {
        return null;
    }

    @Override
    public TransactionResponse withdraw(TransactionRequest request) {
        return null;
    }

    @Override
    public TransactionResponse transfer(TransactionRequest request) {
        return null;
    }
}
