package com.bms.bank_management_system.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerKycRequest {
    @NotBlank
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
    private String panNumber;

    @NotBlank
    @Pattern(regexp = "\\d{12}")
    private String aadharNumber;

    @NotBlank
    private String education;

    @NotBlank
    private String occupation;

    private Boolean hasExistingAccount;
}
