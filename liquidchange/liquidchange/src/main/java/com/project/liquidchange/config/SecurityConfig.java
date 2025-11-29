package com.project.liquidchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable for simplicity in this project
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/login").permitAll() // Public pages
                        .anyRequest().authenticated() // Everything else needs login
                )
                .formLogin(form -> form.defaultSuccessUrl("/", true)) // Redirect to app after login
                .httpBasic(basic -> {}); // Allow API access

        return http.build();
    }

    // Create a Hardcoded User for the Demo
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails driver = User.withDefaultPasswordEncoder()
                .username("raju")
                .password("1234")
                .roles("DRIVER")
                .build();

        return new InMemoryUserDetailsManager(driver);
    }
}