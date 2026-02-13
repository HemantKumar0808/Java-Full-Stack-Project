package com.bms.bank_management_system.service;

import com.bms.bank_management_system.requestDto.AccountCreateRequest;
import com.bms.bank_management_system.responseDto.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(AccountCreateRequest request);
}
