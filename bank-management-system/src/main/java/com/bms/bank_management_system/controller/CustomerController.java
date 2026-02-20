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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Crete customer end point
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(@Valid @RequestBody CustomerSignupRequest request) {
        CustomerResponse response = customerService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // KYC end point
    @PutMapping("/{customerId}/kyc")
    public ResponseEntity<KycCompletionResponse> completeKyc(
            @PathVariable String customerId,
            @Valid @RequestBody CustomerKycRequest request) {
        // Temporary log daal do
        System.out.println("Received request: " + request);

        KycCompletionResponse response = customerService.completeKyc(customerId, request);
        return ResponseEntity.ok(response);
    }
}