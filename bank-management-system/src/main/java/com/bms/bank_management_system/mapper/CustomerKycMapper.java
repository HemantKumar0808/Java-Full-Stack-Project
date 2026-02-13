package com.bms.bank_management_system.mapper;

import com.bms.bank_management_system.entity.CustomerKycInfo;
import com.bms.bank_management_system.requestDto.CustomerKycRequest;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class CustomerKycMapper {

    public CustomerKycInfo toEntity(CustomerKycRequest request) {
        CustomerKycInfo kycInfo = new CustomerKycInfo();
        kycInfo.setPanNumber(request.getPanNumber());
        kycInfo.setAadharNumber(request.getAadharNumber());
        kycInfo.setEducation(request.getEducation());
        kycInfo.setOccupation(request.getOccupation());
        return kycInfo;
    }
}
