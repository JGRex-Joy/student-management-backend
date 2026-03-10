package com.cwm.studentmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Copyright (c) 2026 Mahesh Shet
 * Licensed under the MIT License.
 *
 * FIX 1: Added `throws Exception` — required because HttpSecurity throws checked exceptions.
 * FIX 2: Added /react/** to PUBLIC_PATH so the React bundle (JS/CSS assets) loads
 *         without authentication.
 * NOTE:  CSRF is kept ON. The React app reads the CSRF token from the <meta> tags
 *        injected by the react-app.html template and includes it in POST/PUT headers.
 *        This is why we do NOT disable CSRF for /api/** — it would open CSRF attacks.
 */

@Configuration
@EnableWebSecurity
public class SpringConfig {

    private static final String[] PUBLIC_PATH = {
            "/login",
            "/css/**",
            "/images/**",
            "/js/**",
            "/react/**",   // React build artifacts
            "/error"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}