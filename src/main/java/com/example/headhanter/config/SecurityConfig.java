package com.example.headhanter.config;

import com.example.headhanter.models.Role;
import com.example.headhanter.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()



                        .requestMatchers(HttpMethod.GET, "/resumes").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.GET, "/vacancies").hasRole(Role.APPLICANT.name())

                        .requestMatchers("/", "/register", "/login", "/resumes/**", "/vacancies/**").permitAll()
                        .requestMatchers("/profile/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/users", "/users").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/vacancies/**").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/vacancies/**").hasRole(Role.EMPLOYER.name())
                        .requestMatchers(HttpMethod.GET, "/api/vacancies/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/resumes/**").hasRole(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.PUT, "/api/resumes/**").hasRole(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/resumes/**").hasRole(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET, "/api/resumes/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/responses").hasRole(Role.APPLICANT.name())
                        .requestMatchers("/api/responses/by-vacancy/**", "/api/responses/*/confirm").hasRole(Role.EMPLOYER.name())
                        .requestMatchers("/api/responses/my-vacancies/**").hasRole(Role.APPLICANT.name())

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/profile", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}