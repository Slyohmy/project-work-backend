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
@RequestMapping("/api/")
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
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }
}