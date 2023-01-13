package swizle.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.User;
import swizle.services.interfaces.IUserDataService;

import java.util.UUID;

@Component
public class Validator {
    private final IUserDataService userDataService;

    @Autowired
    public Validator(@Qualifier(Constants.UserServiceQualifier)IUserDataService userDataService){
        this.userDataService = userDataService;
    }

    public void validateSessionKey(String sessionKey) {
        User currentUser;
        try {

            currentUser = this.userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
            validateUser(currentUser);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    private void validateUser(User user) {
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session with the given key does not exist.");
    }

}
