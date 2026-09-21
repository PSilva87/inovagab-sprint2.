package com.inovagab.inovagab.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/health").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/estrategias").hasRole("LIDER")
                        .requestMatchers(HttpMethod.PUT, "/api/estrategias/**").hasRole("LIDER")
                        .requestMatchers(HttpMethod.DELETE, "/api/estrategias/**").hasRole("LIDER")

                        .requestMatchers(HttpMethod.GET, "/api/estrategias/**")
                        .hasAnyRole("OPERADOR", "GESTOR", "LIDER")

                        .requestMatchers(HttpMethod.POST, "/api/ideias").hasRole("OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/api/ideias/minhas").hasRole("OPERADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/ideias/**").hasRole("OPERADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/ideias/**").hasRole("OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/api/ideias").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/ideias/**").hasRole("GESTOR")

                        .requestMatchers(HttpMethod.POST, "/api/projetos").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/projetos/**").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/projetos/**").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/projetos/**").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/projetos/**").hasAnyRole("GESTOR", "LIDER")

                        .requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasRole("LIDER")
                        .requestMatchers(HttpMethod.GET, "/api/auditoria").hasRole("LIDER")
                        .requestMatchers(HttpMethod.GET, "/api/metricas").hasRole("LIDER")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form.disable())

                .httpBasic(basic -> basic.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
