package projectwork.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectwork.backend.model.User;
import projectwork.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        user = new User(user.getFullName(), user.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok("Congratsulations " + user.getFullName() +
                ", you've successfully registered an account.");
    }
}
