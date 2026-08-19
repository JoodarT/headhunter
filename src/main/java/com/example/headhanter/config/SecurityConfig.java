package com.example.headhanter.config;

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
    private final RoleAwareAuthenticationSuccessHandler roleAwareAuthenticationSuccessHandler;

    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            RoleAwareAuthenticationSuccessHandler roleAwareAuthenticationSuccessHandler
    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleAwareAuthenticationSuccessHandler = roleAwareAuthenticationSuccessHandler;
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



                        .requestMatchers(HttpMethod.GET, "/resumes").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/vacancies").hasRole("APPLICANT")

                        .requestMatchers(HttpMethod.GET, "/resumes/create", "/resumes/*/edit").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.POST, "/resumes/create", "/resumes/*/edit", "/resumes/*/delete").hasRole("APPLICANT")

                        .requestMatchers(HttpMethod.GET, "/vacancies/create", "/vacancies/*/edit").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.POST, "/vacancies/create", "/vacancies/*/edit", "/vacancies/*/delete").hasRole("EMPLOYER")

                        .requestMatchers(HttpMethod.POST, "/vacancies/*/respond").hasRole("APPLICANT")

                        .requestMatchers("/", "/register", "/login", "/resumes/**", "/vacancies/**").permitAll()
                        .requestMatchers("/profile/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/users", "/users").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.DELETE, "/api/vacancies/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/api/vacancies/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.PUT, "/api/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.DELETE, "/api/resumes/**").hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.GET, "/api/resumes/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/responses").hasRole("APPLICANT")
                        .requestMatchers("/api/responses/by-vacancy/**", "/api/responses/*/confirm").hasRole("EMPLOYER")
                        .requestMatchers("/api/responses/my-vacancies/**").hasRole("APPLICANT")

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(roleAwareAuthenticationSuccessHandler)
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