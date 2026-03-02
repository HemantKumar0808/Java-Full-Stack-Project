package com.bms.bank_management_system.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Login ID (customerId or email) is required")
    private String loginId;

    @NotBlank(message = "Password is required")
    private String password;
}
