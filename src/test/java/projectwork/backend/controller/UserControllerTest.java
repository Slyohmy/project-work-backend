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
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;
import projectwork.backend.service.UserService;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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
    static String getUsersUrl;
    static String getUserByIdUrl;
    static String registerUserUrl;
    static String updateUserUrl;
    static String deleteUserUrl;

    @BeforeEach
    void setUp() {
        getUsersUrl = "http://localhost:8080/api/v1/user/users";
        getUserByIdUrl = "http://localhost:8080/api/v1/user/1";
        registerUserUrl = "http://localhost:8080/api/v1/user/register";
        updateUserUrl = "http://localhost:8080/api/v1/user/update_profile/1";
        deleteUserUrl = "http://localhost:8080/api/v1/user/delete/1";
    }

    @Test
    void getAllUsers() throws Exception {
        userInfoResponse1 = new UserInfoResponse(1L, "testuser1", "test@user2.se", Collections.singletonList("ROLE_USER"));
        userInfoResponse2 = new UserInfoResponse(2L, "testuser2", "test@user2.se", Collections.singletonList("ROLE_USER"));
        List<UserInfoResponse> userList = Arrays.asList(userInfoResponse1, userInfoResponse2);
        given(service.getAllUsers()).willReturn(userList);

        mvc.perform(get(getUsersUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(userInfoResponse1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(userInfoResponse2.getUsername())));
        verify(service, VerificationModeFactory.times(1)).getAllUsers();
        reset(service);
    }

    @Test
    void getUserById() throws Exception {
        userInfoResponse1 = new UserInfoResponse(1L, "testuser1", "test@user2.se", Collections.singletonList("ROLE_USER"));
        List<UserInfoResponse> userList = Arrays.asList(userInfoResponse1);
        given(service.getUserById(Mockito.any())).willReturn(userList);

        mvc.perform(get(getUserByIdUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(userInfoResponse1.getUsername())));
        verify(service, VerificationModeFactory.times(1)).getUserById(Mockito.any());
        reset(service);
    }

    @Test
    void registerUser() throws Exception {
        signupRequest = new SignupRequest("test", "test@test.se", "123123", Collections.singleton("ROLE_USER"));
        user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        given(service.registerUser(Mockito.any())).willReturn(user);

        mvc.perform(post(registerUserUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("New account: test has been successfully created.")));
        verify(service, VerificationModeFactory.times(1)).registerUser(Mockito.any());
        reset(service);
    }

    @Test
    void updateUser() throws Exception {
        user = new User("test", "test@test.se", "123123");
        user.setId(1L);
        given(service.updateUser(Mockito.anyLong(), Mockito.any())).willReturn("Update was successful!");

        mvc.perform(put(updateUserUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))).andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Update was successful!")));
        verify(service, VerificationModeFactory.times(1)).updateUser(Mockito.anyLong(), Mockito.any());
        reset(service);
    }

    @Test
    void deleteUserById() throws Exception {
        user = new User("test", "test@test.se", "123123");
        user.setId(1L);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        given(service.deleteUserById(Mockito.anyLong())).willReturn(response);

        mvc.perform(delete(deleteUserUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))).andExpect(status().isOk())
                .andExpect(jsonPath("$", is(response)));
        verify(service, VerificationModeFactory.times(1)).deleteUserById(Mockito.anyLong());
        reset(service);
    }
}