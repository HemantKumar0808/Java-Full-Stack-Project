package com.bms.bank_management_system.service;

import com.bms.bank_management_system.requestDto.CustomerCreateRequest;
import com.bms.bank_management_system.responseDto.CustomerResponse;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
}
