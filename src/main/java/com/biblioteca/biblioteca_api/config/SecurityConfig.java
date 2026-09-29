package com.biblioteca.biblioteca_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.security.bibliotecario-password}")
    private String senhaBibliotecario;

    @Value("${app.security.leitor-password}")
    private String senhaLeitor;

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {;
        UserDetails bibliotecario = User.builder()
                .username("bibliotecario")
                .password(passwordEncoder.encode(senhaBibliotecario))
                .roles("BIBLIOTECARIO")
                .build();

        UserDetails leitor = User.builder()
                .username("leitor")
                .password(passwordEncoder.encode(senhaLeitor))
                .roles("LEITOR")
                .build();

        return new InMemoryUserDetailsManager(bibliotecario, leitor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/**").hasRole("BIBLIOTECARIO")
                    .requestMatchers(HttpMethod.PUT, "/**").hasRole("BIBLIOTECARIO")
                    .requestMatchers(HttpMethod.DELETE, "/**").hasRole("BIBLIOTECARIO")
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
