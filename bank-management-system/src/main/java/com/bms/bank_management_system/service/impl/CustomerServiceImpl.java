package com.bms.bank_management_system.service.impl;

import com.bms.bank_management_system.entity.Account;
import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.entity.CustomerKycInfo;
import com.bms.bank_management_system.enums.AccountStatus;
import com.bms.bank_management_system.enums.CustomerStatus;
import com.bms.bank_management_system.exception.ResourceAlreadyExistsException;
import com.bms.bank_management_system.exception.ResourceNotFoundException;
import com.bms.bank_management_system.mapper.CustomerKycMapper;
import com.bms.bank_management_system.mapper.CustomerMapper;
import com.bms.bank_management_system.repository.AccountRepository;
import com.bms.bank_management_system.repository.CustomerRepository;
import com.bms.bank_management_system.requestDto.CustomerSignupRequest;
import com.bms.bank_management_system.requestDto.CustomerKycRequest;
import com.bms.bank_management_system.responseDto.CustomerResponse;
import com.bms.bank_management_system.responseDto.KycCompletionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements com.bms.bank_management_system.service.CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerKycMapper customerKycMapper;
    private final AccountRepository accountRepository;

    @Override
    public CustomerResponse signup(CustomerSignupRequest request) {

        // 1. Validate unique fields
        // Email
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        // Phone no
        if (customerRepository.findByPhoneNo(request.getPhoneNo()).isPresent()) {
            throw new ResourceAlreadyExistsException("Phone number already exists");
        }

        // 2. Create Customer Request -> Customer entity
        Customer customer = customerMapper.toEntity(request);

        // 3. Business logic add
        customer.setCustomerId(generateCustomerId());
        customer.setStatus(CustomerStatus.ACTIVE);

        // 4. Save -> JPA -> DB
        Customer saved = customerRepository.save(customer);

        // 5. Customer entity -> Customer Response
        CustomerResponse response = customerMapper.toResponse(saved);

        // 6. return
        return response;

    }

    @Override
    public KycCompletionResponse completeKyc(String customerId, CustomerKycRequest request) {

        System.out.println("KYC request received for customerId: " + customerId);

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        // Uniqueness check (sirf @Query wale use kar rahe hain)
        if (customerRepository.existsByPanNumber(request.getPanNumber())) {
            throw new ResourceAlreadyExistsException("This PAN is already registered");
        }
        if (customerRepository.existsByAadharNumber(request.getAadharNumber())) {
            throw new ResourceAlreadyExistsException("This Aadhaar is already registered");
        }

        // Mapping
        CustomerKycInfo kycInfo = customerKycMapper.toEntity(request);

        // Link karo
        kycInfo.setCustomer(customer);
        customer.setKycInfo(kycInfo);

        // Save
        customerRepository.save(customer);

        // Accounts activate
        int activatedCount = 0;
        for (Account acc : customer.getAccounts()) {
            if (acc.getAccountStatus() == AccountStatus.PENDING_KYC) {
                acc.setAccountStatus(AccountStatus.ACTIVE);
                activatedCount++;
            }
        }
        if (activatedCount > 0) {
            accountRepository.saveAll(customer.getAccounts());
        }

        // Response
        return KycCompletionResponse.builder()
                .status("SUCCESS")
                .message("KYC successfully completed! " +
                        (activatedCount > 0 ? activatedCount + " account(s) activated." : "Ready for full banking."))
                .accountsActivated(activatedCount > 0)
                .build();
    }


    private String generateCustomerId() {
        // Logic: CUST + timestamp / sequence
        return "CUST-" + System.currentTimeMillis();
    }
}
