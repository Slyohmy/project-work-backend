package projectwork.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projectwork.backend.model.User;
import projectwork.backend.repository.UserRepository;

@Component
public class AdminService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AdminService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        try {
            String ADMIN_FULLNAME = System.getenv("ADMIN_FULLNAME");
            String ADMIN_EMAIL = System.getenv("ADMIN_EMAIL");

            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                User user = new User(ADMIN_FULLNAME, ADMIN_EMAIL);

                userService.registerUser(user);

                logger.info("Default admin account created.");

            }

        } catch (Exception e) {
            logger.error("Error while creating default admin account: {}", e.getMessage());

        }
    }
}
