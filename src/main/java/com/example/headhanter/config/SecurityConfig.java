package com.example.headhanter.config;

import com.example.headhanter.models.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        .requestMatchers("/", "/register", "/profile/**", "/resumes/**", "/vacancies/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/users", "/users").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/vacancies/**").hasAuthority(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**").hasAuthority(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/vacancies/**").hasAuthority(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.GET, "/api/vacancies/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/resumes/**").hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.PUT, "/api/resumes/**").hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/resumes/**").hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET, "/api/resumes/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/responses").hasAuthority(Role.APPLICANT.name())
                        .requestMatchers("/api/responses/by-vacancy/**", "/api/responses/*/confirm").hasAuthority(Role.EMPLOYER.name())
                        .requestMatchers("/api/responses/my-vacancies/**").hasAuthority(Role.APPLICANT.name())

                        .anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}