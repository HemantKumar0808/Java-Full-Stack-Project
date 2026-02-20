package com.bms.bank_management_system.mapper;

import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.entity.CustomerAddress;
import com.bms.bank_management_system.requestDto.CustomerSignupRequest;
import com.bms.bank_management_system.responseDto.CustomerAddressResponse;
import com.bms.bank_management_system.responseDto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerSignupRequest request){
        // Customer Mapping
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setFatherName(request.getFatherName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNo(request.getPhoneNo());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(request.getGender());

        // Address Mapping
        CustomerAddress address = new CustomerAddress();
        address.setHouseNo(request.getHouseNo());
        address.setStreet(request.getStreet());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPinCode(request.getPinCode());
        address.setCountry(request.getCountry());

        // Set
        address.setCustomer(customer);
        customer.setCustomerAddress(address);

        return customer;
    }

    public CustomerResponse toResponse(Customer customer){

        CustomerResponse response = new CustomerResponse();
        response.setCustomerId(customer.getCustomerId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setFatherName(customer.getFatherName());
        response.setEmail(customer.getEmail());
        response.setPhoneNo(customer.getPhoneNo());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setGender(customer.getGender());
        response.setStatus(customer.getStatus());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        CustomerAddressResponse addressResponse = new CustomerAddressResponse();
        addressResponse.setHouseNo(customer.getCustomerAddress().getHouseNo());
        addressResponse.setStreet(customer.getCustomerAddress().getStreet());
        addressResponse.setLandmark(customer.getCustomerAddress().getLandmark());
        addressResponse.setCity(customer.getCustomerAddress().getCity());
        addressResponse.setState(customer.getCustomerAddress().getState());
        addressResponse.setPinCode(customer.getCustomerAddress().getPinCode());
        addressResponse.setCountry(customer.getCustomerAddress().getCountry());

        // set
        response.setAddress(addressResponse);

        // Custom message
        response.setMessage("Details successfully saved, now you can SIGN IN.");
        return response;
    }
}
