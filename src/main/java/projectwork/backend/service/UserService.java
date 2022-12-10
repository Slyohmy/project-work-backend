package projectwork.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import projectwork.backend.model.enums.ERole;
import projectwork.backend.model.Role;
import projectwork.backend.model.User;
import projectwork.backend.payload.SignupRequest;
import projectwork.backend.payload.UserInfoResponse;
import projectwork.backend.repository.RoleRepository;
import projectwork.backend.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserInfoResponse> getAllUsers() {
        return userRepository
                .findAll().stream()
                .map(User::userInfoResponse)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> updateUser(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setUsername(user.getUsername());
            user.setEmail(user.getEmail());
            passwordEncoder.encode(user.getPassword());

            userRepository.save(user);
            return ResponseEntity.ok().body("User: " + user.getUsername() + " was successfully updated.");

        }
        return ResponseEntity.badRequest().body("User with id '" + user.getId() + "' does not exist.");
    }

    public List<UserInfoResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .stream()
                .map(User::userInfoResponse)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '" + id + "' does not exist."));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public void registerAdmin(User user) {
        user = new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()));
        Optional<Role> role = roleRepository.findByRole(ERole.ROLE_ADMIN);
        user.getRoles().add(role.get());
        userRepository.save(user);
    }

    public User registerUser(SignupRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username '" + signUpRequest.getUsername() + "' already exists.");
        }
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email '" + signUpRequest.getEmail() + "' already exists.");
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });

            user.setRoles(roles);
            userRepository.save(user);
        }
        return user;
    }
}
