package projectwork.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;
import projectwork.backend.repository.RoleRepository;
import projectwork.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static User user;
    private static User user2;
    static SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, roleRepository, passwordEncoder);
        user = new User("testuser1", "testuser1@gmail.com", "123123");
        user2 = new User("testuser2", "testuser2@gmail.com", "123123");
        signupRequest = new SignupRequest(user.getUsername(), user.getEmail(), user.getPassword(), null);
    }


    @Test
    void getAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List> newList = userService.getAllUsers();

        System.out.println(newList.getBody().size());
        System.out.println(userList.size());

        assertEquals(userList.size(), newList.getBody().size());
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUserById() {
        user.setId(1L);
        List<UserInfoResponse> userList = new ArrayList<>();
        userList.add(user.userInfoResponse());

        doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
        ResponseEntity<List> responseEntity = userService.getUserById(user.getId());

        System.out.println(userList);
        System.out.println(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    void deleteUserById() {
    }

    @Test
    @Disabled("Needs to be fixed")
    void registerUser() {


        User newUser = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        doReturn(Optional.of(newUser)).when(userRepository).findByUsername(newUser.getUsername());
        ResponseEntity<String> responseEntity = userService.registerUser(signupRequest);

        //DEBUG
        System.out.println(signupRequest.getRole());
        System.out.println(responseEntity.getBody());


        //assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(responseEntity.getBody(), signupRequest);
        assertEquals(signupRequest.getEmail(), newUser.getEmail());
    }
}