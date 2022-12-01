package projectwork.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projectwork.backend.enums.ERole;
import projectwork.backend.model.Role;
import projectwork.backend.model.User;
import projectwork.backend.repository.RoleRepository;
import projectwork.backend.repository.UserRepository;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public Runner(UserRepository userRepository, UserService userService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.findByRole(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if (roleRepository.findByRole(ERole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));

            try {
                String ADMIN_FULLNAME = System.getenv("ADMIN_FULLNAME");
                String ADMIN_EMAIL = System.getenv("ADMIN_EMAIL");
                String ADMIN_PASSWORD = System.getenv("ADMIN_PASSWORD");

                if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                    User user = new User(ADMIN_FULLNAME, ADMIN_EMAIL, ADMIN_PASSWORD);

                    userService.registerAdmin(user);

                    logger.info("Default admin account created.");

                }

            } catch (Exception e) {
                logger.error("Error while creating default admin account: {}", e.getMessage());

            }
        }
    }
}
