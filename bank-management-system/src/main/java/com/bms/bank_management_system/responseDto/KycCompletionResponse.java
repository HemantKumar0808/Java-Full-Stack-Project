package com.bms.bank_management_system.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycCompletionResponse {
    private String status;           // "SUCCESS" ya "FAILED"
    private String message;
    private boolean accountsActivated;
}
