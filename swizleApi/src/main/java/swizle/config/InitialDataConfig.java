package swizle.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swizle.models.Lecture;
import swizle.models.User;
import swizle.repositories.ILectureRepository;
import swizle.repositories.IUserRepository;
import swizle.utils.PasswordCrypto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Configuration
public class InitialDataConfig {
    @Bean
    public CommandLineRunner commandLineRunner(IUserRepository userRepository, ILectureRepository lectureRepository, PasswordCrypto passwordCrypto) {
        return args -> {
            userRepository.save(new User(1, "admin", passwordCrypto.encryptPassword("12345"), true));
            lectureRepository.save(
                    new Lecture(
                            1,
                            "J. angielski",
                            DayOfWeek.MONDAY,
                            LocalTime.of(12, 15),
                            Duration.ofMinutes(90)
                    )
            );
        };
    }
}
