package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 🔥 Desactiva CSRF para permitir POST desde Postman
                .authorizeRequests()
                .antMatchers("/api/**").permitAll() // Permite acceso libre a las rutas /api/**
                .anyRequest().permitAll() // También permite el resto
                .and()
                .formLogin().disable() // Desactiva formulario de login
                .httpBasic().disable(); // Desactiva Basic Auth (si quieres probar sin login)

        return http.build();
    }
}

