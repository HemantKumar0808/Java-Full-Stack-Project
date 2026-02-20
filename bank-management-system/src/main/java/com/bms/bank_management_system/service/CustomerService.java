package com.bms.bank_management_system.service;

import com.bms.bank_management_system.requestDto.CustomerSignupRequest;
import com.bms.bank_management_system.requestDto.CustomerKycRequest;
import com.bms.bank_management_system.responseDto.CustomerResponse;
import com.bms.bank_management_system.responseDto.KycCompletionResponse;

public interface CustomerService {
    CustomerResponse signup(CustomerSignupRequest request);

    KycCompletionResponse completeKyc(String customerId, CustomerKycRequest request);
}
