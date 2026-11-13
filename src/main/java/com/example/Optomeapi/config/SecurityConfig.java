package com.example.Optomeapi.config;

import com.example.Optomeapi.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(f -> f.sameOrigin())) // allow H2 console iframe
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // === Public: Swagger / OpenAPI / H2 ===
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/h2-console/**")
                        .permitAll()

                        // === Public: auth endpoints ===
                        .requestMatchers(
                                "/auth/**",
                                "/api/auth/**",
                                "/api/register",
                                "/api/login")
                        .permitAll()

                        // === Role-based endpoints — BOTH path styles ===
                        .requestMatchers("/patient/**", "/api/patient/**").hasAnyRole("PATIENT", "ADMIN")
                        .requestMatchers("/doctor/**", "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")

                        // === Authenticated only ===
                        .requestMatchers("/messages/**", "/api/messages/**").authenticated()
                        .requestMatchers("/notifications/**", "/api/notifications/**").authenticated()
                        .requestMatchers("/user", "/api/user").authenticated()
                        .requestMatchers("/logout", "/api/logout").authenticated()

                        // === Everything else ===
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}