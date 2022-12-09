package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.Session;
import swizle.models.User;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;

import java.util.List;
import java.util.UUID;

@RestController
@ResponseBody
public class UserController {
    @Qualifier(Constants.FakeUserServiceQualifier)
    private final IUserDataService userDataService;

    @Autowired
    public UserController(IUserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("/api/user")
    public List<User> getUsers() {
        return userDataService.getItems();
    }

    @GetMapping("/api/user/isadmin")
    public boolean isActiveUserAdmin(String sessionKey) {
        User activeUser;

        try {
            activeUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or empty session key.");
        }

        if(activeUser == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Log in required to perform this action.");

        return activeUser.isAdmin();
    }

    @GetMapping("/api/user/sessions")
    public List<Session> getSessions() {
        return userDataService.getSessions();
    }

    @PostMapping("/api/user/register")
    public void register(String name, String password) {
        userDataService.addItem(new User(name, password));
    }

    @PostMapping("/api/user/login")
    public String logIn(String name, String password) {
        return userDataService
                .startSession(userDataService.getUserByNameAndPassword(name, password))
                .toString();
    }

    @DeleteMapping("/api/user/logout")
    public void logOut(String sessionKey) {
        userDataService.endSession(UUID.fromString(sessionKey));
    }
}
