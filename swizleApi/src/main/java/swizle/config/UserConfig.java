package swizle.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swizle.models.User;
import swizle.repositories.IUserRepository;

@Configuration
public class UserConfig {
    @Bean
    public CommandLineRunner commandLineRunner(IUserRepository repository) {
        return args -> {
            repository.save(new User(1, "admin", "12345", true));
        };
    }
}
