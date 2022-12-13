package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.Session;
import swizle.models.User;
import swizle.models.dto.SessionResponseDto;
import swizle.models.dto.UserDto;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;

import java.util.List;
import java.util.UUID;

import swizle.utils.dtoConverters.UserDtoConverter;

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

    @PostMapping(value = "/api/user/register", headers = { "content-type=application/json" })
    public void register(@RequestBody UserDto userCredentials) {
        userDataService.addItem(UserDtoConverter.toModel(userCredentials));
    }

    @PostMapping(value = "/api/user/login", headers = { "content-type=application/json" })
    public SessionResponseDto logIn(@RequestBody UserDto userCredentials) {
        Session startedSession = userDataService.startSession(UserDtoConverter.toModel(userCredentials));
        return new SessionResponseDto(startedSession.getId(),
                userDataService.getUserByNameAndPassword(userCredentials.getName(), userCredentials.getPassword()).isAdmin());
    }

    @DeleteMapping(value = "/api/user/logout", headers = { "content-type=application/json" })
    public void logOut(@RequestBody SessionResponseDto sessionKey) {
        userDataService.endSession(sessionKey.getSessionKey());
    }
}
