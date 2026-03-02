com.bms.bank_management_system
└── security
├── config
│   └── SecurityConfig.java             // main security rules (filter chain, permitAll, etc.)
├── filter
│   └── JwtAuthenticationFilter.java    // token check aur authentication set karne ka filter
├── util
│   └── JwtUtil.java                    // token generate, validate, extract claims (customerId, role)
├── service
│   └── CustomUserDetailsService.java   // UserDetails load karne ka service (DB se customer load)
└── dto
└── AuthResponse.java               // login ke baad token + message return karne ke liye

Step 1 (Abhi kar lo)

security package banao (com.bms.bank_management_system.security)
Uske andar config, filter, util, service, dto sub-packages banao
pom.xml mein dependencies add kar lo (agar nahi kiye to):

XML<!-- Spring Security -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>