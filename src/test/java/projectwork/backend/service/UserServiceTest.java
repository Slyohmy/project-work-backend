package projectwork.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import projectwork.backend.model.User;
import projectwork.backend.payload.UserInfoResponse;
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
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void getAllUsers() {
        User user1 = new User("testuser1","testuser1@gmail.com","123123");
        User user2 = new User("testuser2","testuser2@gmail.com","123123");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List> newList = userService.getAllUsers();

        System.out.println(newList.getBody().size());
        System.out.println(userList.size());

        assertEquals(userList.size(),newList.getBody().size());
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUserById() {
        User user1 = new User("testuser1","testuser1@gmail.com","123123");
        user1.setId(1L);
        List<UserInfoResponse> userList = new ArrayList<>();
        userList.add(user1.userInfoResponse());

        doReturn(Optional.of(user1)).when(userRepository).findById(user1.getId());
        ResponseEntity<List> responseEntity = userService.getUserById(user1.getId());

        System.out.println(userList);
        System.out.println(responseEntity.getBody());

        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    void deleteUserById() {
    }
}