package projectwork.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import projectwork.backend.model.User;
import projectwork.backend.payload.LoginRequest;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;
import projectwork.backend.service.AuthService;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthService service;
    @MockBean
    private AuthenticationManager authenticationManager;
    static String registerUrl;
    static String loginUrl;
    static UserInfoResponse userInfoResponse;
    static SignupRequest signupRequest;
    static User user;

    @BeforeEach
    void setUp() {
        registerUrl = "http://localhost:8080/api/v1/register";
        loginUrl = "http://localhost:8080/api/v1/login";
    }

    @Test
    @Disabled("Issues with ResponseEntity return value")
    void login() throws Exception {
        user = new User("slymo", "test@test.se", "123123");
        userInfoResponse = new UserInfoResponse(1L, user.getUsername(), user.getEmail(), Collections.singletonList("ROLE_USER"));
        LoginRequest loginRequest = new LoginRequest(user.getUsername(), user.getPassword());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        given(service.authenticateUser(Mockito.any())).willReturn((ResponseEntity<UserInfoResponse>) authentication);

        mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", is(userInfoResponse)));
        verify(service, VerificationModeFactory.times(1)).authenticateUser(loginRequest);
        reset(service);
    }

    @Test
    void logout() {
    }

    @Test
    void signup() throws Exception {
        signupRequest = new SignupRequest("test", "test@test.se", "123123", Collections.singleton("ROLE_USER"));
        user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        given(service.signup(Mockito.any())).willReturn("Congratsulations " + signupRequest.getUsername() + ", you've successfully registered an account.");

        mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Congratsulations " + signupRequest.getUsername() + ", you've successfully registered an account.")));
        verify(service, VerificationModeFactory.times(1)).signup(Mockito.any());
        reset(service);
    }
}