package projectwork.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<List> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by Id")
    public ResponseEntity<List> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    @Operation(summary = "Create new user or admin account")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        userService.registerUser(signupRequest);
        return ResponseEntity.ok("New account: " + signupRequest.getUsername() +
                " has been successfully created.");
    }

    @PutMapping("/update_profile/{id}")
    @Operation(summary = "Update a user profile")
    public ResponseEntity<String> updateUser(@PathVariable(value = "id", required = false) Long id,
                                        @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
