package swizle.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordCrypto {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());

    public String encryptPassword(String password) {
        return encoder.encode(password);
    }

    public boolean passwordMatches(String plainPassword, String encryptedPassword) {
        return encoder.matches(plainPassword, encryptedPassword);
    }
}
