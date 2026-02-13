package com.bms.bank_management_system.requestDto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerKycRequest {
//    @NotBlank(message = "PAN number is required")
//    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String panNumber;

//    @NotBlank(message = "Aadhaar number is required")
//    @Pattern(regexp = "\\d{12}", message = "Aadhaar must be 12 digits")
    private String aadharNumber;

    @NotBlank(message = "Education is required")
    private String education;

    @NotBlank(message = "Occupation is required")
    private String occupation;
}