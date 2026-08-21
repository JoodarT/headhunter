package com.example.headhanter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationFlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registeredUser_canLogInWithSamePassword() throws Exception {
        String email = "auth_ok_" + System.nanoTime() + "@example.com";
        String password = "Password123";
        register(email, password, "APPLICANT");

        login(email, password, "APPLICANT")
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vacancies"));
    }

    @Test
    void wrongPassword_isRejected() throws Exception {
        String email = "auth_bad_" + System.nanoTime() + "@example.com";
        String password = "Password123";
        register(email, password, "APPLICANT");

        login(email, "totally-wrong-password", "APPLICANT")
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void loginWithDifferentEmailCase_stillSucceeds() throws Exception {
        String email = "auth_case_" + System.nanoTime() + "@example.com";
        String password = "Password123";
        register(email, password, "APPLICANT");

        login(email.toUpperCase(), password, "APPLICANT")
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vacancies"));
    }

    @Test
    void employer_canLogInAndIsRedirectedToResumes() throws Exception {
        String email = "auth_employer_" + System.nanoTime() + "@example.com";
        String password = "Password123";
        register(email, password, "EMPLOYER");

        login(email, password, "EMPLOYER")
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resumes"));
    }

    private void register(String email, String password, String accountType) throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("email", email)
                        .param("password", password)
                        .param("name", "Test User")
                        .param("accountType", accountType))
                .andExpect(status().is3xxRedirection());
    }

    private ResultActions login(String email, String password, String role) throws Exception {
        return mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", email)
                .param("password", password)
                .param("role", role));
    }
}
