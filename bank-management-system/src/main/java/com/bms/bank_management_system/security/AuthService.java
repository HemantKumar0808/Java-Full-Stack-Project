package com.bms.bank_management_system.security;

import com.bms.bank_management_system.responseDto.AuthResponse;
import com.bms.bank_management_system.requestDto.LoginRequest;
import com.bms.bank_management_system.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLoginId(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                    .orElse("CUSTOMER");

            String token = jwtUtil.generateToken(userDetails.getUsername(), role);

            log.info("User logged in: {}", userDetails.getUsername());

            return AuthResponse.builder()
                    .token(token)
                    .build();

        }catch (BadCredentialsException e) {
            log.warn("Invalid credentials for user: {}", request.getLoginId());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
