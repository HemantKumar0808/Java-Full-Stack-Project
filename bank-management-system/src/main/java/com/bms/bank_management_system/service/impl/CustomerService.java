package com.bms.bank_management_system.service.impl;

import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.entity.CustomerAddress;
import com.bms.bank_management_system.enums.CustomerStatus;
import com.bms.bank_management_system.exception.ResourceAlreadyExistsException;
import com.bms.bank_management_system.repository.CustomerRepository;
import com.bms.bank_management_system.requestDto.CustomerCreateRequest;
import com.bms.bank_management_system.responseDto.CustomerAddressResponse;
import com.bms.bank_management_system.responseDto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements com.bms.bank_management_system.service.CustomerService {
    private final CustomerRepository customerRepository;

    private String generateCustomerId() {
        // Logic: CUST + timestamp ya sequence
        return "CUST-" + System.currentTimeMillis();
    }


    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        // Validate unique fields
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        if (customerRepository.findByPhoneNo(request.getPhoneNo()).isPresent()) {
            throw new ResourceAlreadyExistsException("Phone number already exists");
        }

        // Map request to entity
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setFatherName(request.getFatherName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNo(request.getPhoneNo());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(request.getGender());
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setCustomerId(generateCustomerId());

        // Address mapping
        CustomerAddress address = new CustomerAddress();
        address.setHouseNo(request.getHouseNo());
        address.setStreet(request.getStreet());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPinCode(request.getPinCode());
        address.setCountry(request.getCountry());

        // set
        address.setCustomer(customer);
        customer.setCustomerAddress(address);

        // save
        Customer saved = customerRepository.save(customer);

        // Map to response
        CustomerResponse response = new CustomerResponse();
        response.setId(saved.getId());
        response.setCustomerId(saved.getCustomerId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        response.setFatherName(saved.getFatherName());
        response.setEmail(saved.getEmail());
        response.setPhoneNo(saved.getPhoneNo());
        response.setDateOfBirth(saved.getDateOfBirth());
        response.setGender(saved.getGender());
        response.setStatus(saved.getStatus());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        CustomerAddressResponse addressResponse = new CustomerAddressResponse();
        addressResponse.setHouseNo(saved.getCustomerAddress().getHouseNo());
        addressResponse.setStreet(saved.getCustomerAddress().getStreet());
        addressResponse.setLandmark(saved.getCustomerAddress().getLandmark());
        addressResponse.setCity(saved.getCustomerAddress().getCity());
        addressResponse.setState(saved.getCustomerAddress().getState());
        addressResponse.setPinCode(saved.getCustomerAddress().getPinCode());
        addressResponse.setCountry(saved.getCustomerAddress().getCountry());
        response.setAddress(addressResponse);

        // Custom message
        response.setMessage("Details successfully, Need KYC for services");

        return response;

    }
}
