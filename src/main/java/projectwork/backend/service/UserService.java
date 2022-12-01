package projectwork.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import projectwork.backend.enums.ERole;
import projectwork.backend.model.Role;
import projectwork.backend.model.User;
import projectwork.backend.repository.RoleRepository;
import projectwork.backend.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email '" + user.getEmail() + "' already exists.");
        }
        user = new User(user.getFullName(), user.getEmail(), passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByRole(ERole.ROLE_USER);
        user.getRoles().add(role.get());

        userRepository.save(user);
        return ResponseEntity.ok("Congratsulations " + user.getFullName() +
                ", you've successfully registered an account.");
    }

    public ResponseEntity<?> updateUser(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setFullName(user.getFullName());
            user.setEmail(user.getEmail());
            passwordEncoder.encode(user.getPassword());

            userRepository.save(user);
            return ResponseEntity.ok(user);

        }
        return ResponseEntity.badRequest().body("User with id '" + id + "' does not exist.");

    }

    public ResponseEntity<User> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '" + id + "' does not exist."));
        return ResponseEntity.ok(user);
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

        user = new User(user.getFullName(), user.getEmail(), passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByRole(ERole.ROLE_ADMIN);
        user.getRoles().add(role.get());

        userRepository.save(user);
        return ResponseEntity.ok("Congratsulations " + user.getFullName() +
                ", you've successfully registered an account.");
    }
}
