package projectwork.backend.service.Util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projectwork.backend.model.enums.ERole;
import projectwork.backend.model.Role;
import projectwork.backend.model.User;
import projectwork.backend.repository.RoleRepository;
import projectwork.backend.repository.UserRepository;
import projectwork.backend.service.UserService;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Value("${sly.app.adminUsername}")
    private String adminUsername;

    @Value("${sly.app.adminEmail}")
    private String adminEmail;

    @Value("${sly.app.adminPassword}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        if (roleRepository.findByRole(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if (roleRepository.findByRole(ERole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));

            try {
                if (userRepository.findByEmail(adminEmail).isEmpty()) {
                    User user = new User(adminUsername, adminEmail, adminPassword);

                    userService.registerAdmin(user);

                    logger.info("Default admin account created.");
                }

            } catch (Exception e) {
                logger.error("Error while creating default admin account: {}", e.getMessage());

            }
        }
    }
}
