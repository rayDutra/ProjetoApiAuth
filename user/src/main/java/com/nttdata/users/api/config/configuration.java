package com.nttdata.users.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class configuration {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){ // NEEDED TO ALLOW PASSWORD ENCODER INSIDE SECURITY
        return new BCryptPasswordEncoder();
    }

}
