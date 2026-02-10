package com.bms.bank_management_system.requestDto;

import com.bms.bank_management_system.enums.AccountStatus;
import com.bms.bank_management_system.enums.AccountType;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountCreateRequest {


    private AccountType accountType;
    private BigDecimal balance = BigDecimal.ZERO;
    private AccountStatus accountStatus = AccountStatus.ACTIVE;


}
