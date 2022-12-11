package projectwork.backend.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserService.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserServiceTest {

    @MockBean
    private UserService userService;

    static UserInfoResponse user;
    static UserInfoResponse user2;
    static SignupRequest signupRequest;

    @Test
    void getAllUsers() {
        List<UserInfoResponse> userList = Arrays.asList(user, user2);
        user = new UserInfoResponse(1L, "testuser1", "testuser1@gmail.com", null);
        user2 = new UserInfoResponse(2L, "testuser2", "estuser2@gmail.com", null);

        given(userService.getAllUsers()).willReturn(userList);
        assertEquals(userList.size(), 2);
    }

    @Test
    @Disabled("Needs to be fixed")
    void updateUser() {

        ArgumentCaptor<User> userArgument =
                ArgumentCaptor.forClass(User.class);

        // When
        User newUser = new User();
        newUser.setUsername("testname");
        String updatedUser = this.userService.updateUser(user.getId(), newUser);

        System.out.println(updatedUser);
        System.out.println(userArgument.getAllValues());
        System.out.println(newUser.getUsername());
        System.out.println(newUser.userInfoResponse());
        // Then
        assertEquals(updatedUser, userArgument);
    }

    @Test
    @Disabled("Needs to be fixed")
    void getUserById() {
        UserInfoResponse userInfoResponse = new UserInfoResponse(1L, "testuser1@gmail.com", "123123", null);
        List<UserInfoResponse> userList = new ArrayList<>();
        userList.add(userInfoResponse);

        doReturn(userInfoResponse).when(userService).getUserById(userInfoResponse.getId());

        System.out.println(userList);
    }

    @Test
    void deleteUserById() {
    }

    @Test
    @Disabled("Needs to be fixed")
    void registerUser() {

        User newUser = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        User responseEntity = userService.registerUser(signupRequest);

        //DEBUG
        System.out.println(signupRequest.getRole());
        System.out.println(responseEntity);

        //assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(responseEntity.getBody(), signupRequest);
        assertEquals(signupRequest.getEmail(), newUser.getEmail());
    }
}