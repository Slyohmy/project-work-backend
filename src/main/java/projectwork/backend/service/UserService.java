package projectwork.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import projectwork.backend.model.User;
import projectwork.backend.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email '" + user.getEmail() + "' already exists.");
        }

        user = new User(user.getFullName(), user.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok("Congratsulations " + user.getFullName() +
                ", you've successfully registered an account.");
    }

    public ResponseEntity<?> updateUser(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setFullName(user.getFullName());
            user.setEmail(user.getEmail());

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
}
