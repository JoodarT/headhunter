package com.example.headhanter.config;

import com.example.headhanter.models.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        .requestMatchers(HttpMethod.POST, "/vacancies/**").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.PUT, "/vacancies/**").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.DELETE, "/vacancies/**").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.GET, "/vacancies/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/resumes/**").hasRole(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.PUT, "/resumes/**").hasRole(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.DELETE, "/resumes/**").hasRole(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET, "/resumes/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/responses").hasRole(Role.APPLICANT.name())
                        .requestMatchers("/responses/by-vacancy/**", "/responses/*/confirm").hasRole(Role.EMPLOYER.name())
                        .requestMatchers("/responses/my-vacancies/**").hasRole(Role.APPLICANT.name())

                        .requestMatchers("/users/**").hasRole(Role.ADMIN.name())

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}