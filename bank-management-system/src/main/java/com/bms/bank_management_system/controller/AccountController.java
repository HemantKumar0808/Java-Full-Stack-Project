package com.bms.bank_management_system.controller;

import com.bms.bank_management_system.requestDto.AccountCreateRequest;
import com.bms.bank_management_system.responseDto.AccountResponse;

import com.bms.bank_management_system.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<AccountResponse> createAccount(Authentication authentication, @Valid @RequestBody AccountCreateRequest request) {
        String customerId = authentication.getName();
        AccountResponse response = accountService.createAccount(customerId, request);
        return ResponseEntity.ok(response);
    }
}