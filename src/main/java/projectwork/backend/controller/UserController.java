package projectwork.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectwork.backend.model.User;
import projectwork.backend.repository.UserRepository;
import projectwork.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register_user")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/register_admin")
    @Operation(summary = "Register a new admin")
    public ResponseEntity<String> registerAdmin(@RequestBody User user) {
        return userService.registerAdmin(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by Id")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update_profile/{id}")
    @Operation(summary = "Update a user profile")
    public ResponseEntity<?> updateUser(@RequestParam Long id,
                                        @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
