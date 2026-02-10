package com.bms.bank_management_system.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddressResponse {

    private String houseNo;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private String pinCode;
    private String country;


}
