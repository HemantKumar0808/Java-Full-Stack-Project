package com.bms.bank_management_system.controller;

import com.bms.bank_management_system.requestDto.CustomerSignupRequest;
import com.bms.bank_management_system.requestDto.CustomerKycRequest;
import com.bms.bank_management_system.responseDto.CustomerResponse;
import com.bms.bank_management_system.responseDto.KycCompletionResponse;
import com.bms.bank_management_system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Signup customer
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(@Valid @RequestBody CustomerSignupRequest request) {
        CustomerResponse response = customerService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/kyc")
    public ResponseEntity<KycCompletionResponse> completeKyc(Authentication authentication, @Valid @RequestBody CustomerKycRequest request) {

//        manually
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String customerId = authentication.getName();

        System.out.println(customerId);

        KycCompletionResponse response = customerService.completeKyc(customerId, request);

        return ResponseEntity.ok(response);
    }
}