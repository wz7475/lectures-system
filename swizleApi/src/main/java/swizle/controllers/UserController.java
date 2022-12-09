package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import swizle.models.Session;
import swizle.models.User;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;

import java.util.List;
import java.util.UUID;

@RestController
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
        User activeUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
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
