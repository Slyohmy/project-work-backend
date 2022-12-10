package projectwork.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;
import projectwork.backend.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;
    static User user;
    static UserInfoResponse userInfoResponse1;
    static UserInfoResponse userInfoResponse2;
    static SignupRequest signupRequest;

    @Test
    void getAllUsers() throws Exception {
        userInfoResponse1 = new UserInfoResponse(1L, "testuser1", "test@user2.se", null);
        userInfoResponse2 = new UserInfoResponse(2L, "testuser2", "test@user2.se", null);
        List<UserInfoResponse> userList = Arrays.asList(userInfoResponse1, userInfoResponse2);
        given(service.getAllUsers()).willReturn(userList);

        mvc.perform(get("/api/v1/user/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(userInfoResponse1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(userInfoResponse2.getUsername())));
        verify(service, VerificationModeFactory.times(1)).getAllUsers();
        reset(service);
    }

    @Test
    void getUserById() {
    }

    @Test
    void registerUser() throws Exception {
        signupRequest = new SignupRequest("test", "test@test.se", "123123", null);
        user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        given(service.registerUser(Mockito.any())).willReturn(user);

        mvc.perform(post("/api/v1/user/register").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("New account: test has been successfully created.")));
        verify(service, VerificationModeFactory.times(1)).registerUser(Mockito.any());
        reset(service);
    }

    @Test
    void updateUser() {
    }
}