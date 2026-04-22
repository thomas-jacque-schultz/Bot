package schultz.thomas.discord.bot.model.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import schultz.thomas.discord.bot.config.security.AuthAdminProperties;
import schultz.thomas.discord.bot.model.entity.UserEntity;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.Objects;

@Configuration
public class UserRepositorySeeding {

    public UserRepository userRepository;

    @Value("${discord.admin.id}")
    private String adminDiscordId;

    @Value("${discord.admin.username}")
    private String adminDiscordUsername;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder,
                                   AuthAdminProperties authAdminProperties) {
        return args -> {
            UserEntity admin = new UserEntity();
            admin.setDiscordId(adminDiscordId);
            admin.setDiscordUsername(adminDiscordUsername);
            admin.setAuthUsername(authAdminProperties.username());
            String configuredPassword = authAdminProperties.password();
            String passwordHash = configuredPassword.startsWith("$2a$")
                    || configuredPassword.startsWith("$2b$")
                    || configuredPassword.startsWith("$2y$")
                    ? configuredPassword
                    : passwordEncoder.encode(configuredPassword);
            admin.setPasswordHash(passwordHash);
            admin.setPrivilege(UserPrivilegeEnum.OWNER);
            UserEntity existingUser = userRepository.findByDiscordId(admin.getDiscordId());
            if (Objects.isNull(existingUser)) {
                userRepository.save(admin);
                System.out.println("Admin user created.");
            } else {
                if (existingUser.getAuthUsername() == null || existingUser.getAuthUsername().isBlank()) {
                    existingUser.setAuthUsername(admin.getAuthUsername());
                }
                if (existingUser.getPasswordHash() == null || existingUser.getPasswordHash().isBlank()) {
                    existingUser.setPasswordHash(admin.getPasswordHash());
                }
                userRepository.save(existingUser);
            }
        };
    }
}
