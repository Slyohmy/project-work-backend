package projectwork.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectwork.backend.payload.LoginRequest;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;
import projectwork.backend.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login with an existing account")
    public ResponseEntity<UserInfoResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout")
    public ResponseEntity<?> logout() {
        return authService.logout();
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user account")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }
}
