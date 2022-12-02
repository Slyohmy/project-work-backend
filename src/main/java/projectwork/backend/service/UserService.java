package projectwork.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import projectwork.backend.model.enums.ERole;
import projectwork.backend.model.Role;
import projectwork.backend.model.User;
import projectwork.backend.repository.RoleRepository;
import projectwork.backend.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userRepository.findAll()
                .stream()
                .map(User::userInfoResponse)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email '" + user.getEmail() + "' already exists.");
        }
        user = new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByRole(ERole.ROLE_USER);
        user.getRoles().add(role.get());

        userRepository.save(user);
        return ResponseEntity.ok("Congratsulations " + user.getUsername() +
                ", you've successfully registered an account.");
    }

    public ResponseEntity<?> updateUser(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setUsername(user.getUsername());
            user.setEmail(user.getEmail());
            passwordEncoder.encode(user.getPassword());

            userRepository.save(user);
            return ResponseEntity.ok(user);

        }
        return ResponseEntity.badRequest().body("User with id '" + id + "' does not exist.");
    }

    public ResponseEntity<?> getUserById(Long id) {
        return ResponseEntity.ok().body(userRepository.findById(id)
                .stream()
                .map(User::userInfoResponse)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<?> deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '" + id + "' does not exist."));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<String> registerAdmin(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email '" + user.getEmail() + "' already exists.");
        }

        user = new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByRole(ERole.ROLE_ADMIN);
        user.getRoles().add(role.get());

        userRepository.save(user);
        return ResponseEntity.ok("Congratsulations " + user.getUsername() +
                ", you've successfully registered an account.");
    }
}
