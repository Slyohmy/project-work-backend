package projectwork.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectwork.backend.payload.LoginRequest;
import projectwork.backend.service.LoginService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        return loginService.authenticateUser(loginRequest);
    }
}
