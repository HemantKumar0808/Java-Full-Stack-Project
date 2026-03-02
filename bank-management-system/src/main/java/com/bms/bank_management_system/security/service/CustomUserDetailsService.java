package com.bms.bank_management_system.security.service;

import com.bms.bank_management_system.entity.Customer;
import com.bms.bank_management_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByCustomerId(username)
                .orElseGet(() -> customerRepository.findByEmail(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found with ID or Email: " + username)
                        ));
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + customer.getRole())
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(customer.getCustomerId())
                .password(customer.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
