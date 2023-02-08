package projectwork.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import projectwork.backend.JsonUtil;
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.service.AuthService;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthService service;
    static String registerUrl;
    static String loginUrl;
    static String logoutUrl;
    static SignupRequest signupRequest;


    @BeforeEach
    public void setUp() {
        registerUrl = "/api/v1/auth/register";
        loginUrl = "/api/v1/auth/login";
        logoutUrl = "/api/v1/auth/logout";
    }


    @Test
    void signup() throws Exception {
        signupRequest = new SignupRequest("test", "test@test.se", "123123", Collections.singleton("ROLE_USER"));
        User expectedUser = new User("test", "test@test.se", "123123");
        given(service.signup(Mockito.any())).willReturn(expectedUser);

        MvcResult result = mvc.perform(post(registerUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(signupRequest)))
                .andExpect(status().isOk())
                .andReturn();

        User actualUser = JsonUtil.fromJson(result.getResponse().getContentAsByteArray(), User.class);
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);

        verify(service, VerificationModeFactory.times(1)).signup(Mockito.any());
        reset(service);
    }
}