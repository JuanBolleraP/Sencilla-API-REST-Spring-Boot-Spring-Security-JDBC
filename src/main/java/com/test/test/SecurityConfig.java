package com.test.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    // Asegurar a spring que las peticiones estarán aseguradas
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/empleados/**") // Requiere autenticación para URLs que comienzan con /empleados
                        .hasRole("ADMIN")// Solo usuarios con rol ADMIN pueden acceder a /admin/**
                        .requestMatchers("/auth")
                        .permitAll())
                .httpBasic(Customizer.withDefaults())   // Habilita la autenticación básica HTTP
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Configura BCryptPasswordEncoder para la codificación de contraseñas
    }

    @Bean
    UserDetailsService usuarioPrueba(PasswordEncoder passwordEncoder){
        UserDetails usuario = User.builder()
                .username("Juan")
                .password(passwordEncoder.encode("contra123")) // Usa BCryptPasswordEncoder para codificar la contraseña
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(usuario);
    }
}
