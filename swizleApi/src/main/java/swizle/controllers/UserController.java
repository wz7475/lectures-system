package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.Session;
import swizle.models.dto.LogInResponseDto;
import swizle.models.dto.SessionDto;
import swizle.models.dto.UserDto;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import swizle.utils.Validator;
import swizle.utils.dtoConverters.SessionDtoConverter;
import swizle.utils.dtoConverters.UserDtoConverter;

@RestController
@ResponseBody
public class UserController {
    private final IUserDataService userDataService;
    private final Validator validator;

    @Autowired
    public UserController(@Qualifier(Constants.UserServiceQualifier) IUserDataService userDataService,
                          Validator validator) {
        this.userDataService = userDataService;
        this.validator = validator;
    }

    @GetMapping("/api/user")
    public List<UserDto> getUsers() {
        List<UserDto> response = new ArrayList<>();
        userDataService.getItems().forEach(user -> response.add(UserDtoConverter.toDto(user)));
        return response;
    }

    @GetMapping("/api/user/isadmin")
    public boolean isActiveUserAdmin(String sessionKey) {
        validator.validateSessionKey(sessionKey);
        return userDataService.getUserBySessionKey(UUID.fromString(sessionKey)).isAdmin();
    }

    @GetMapping("/api/user/sessions")
    public List<SessionDto> getSessions() {
        List<SessionDto> response = new ArrayList<>();
        userDataService.getSessions().forEach(session -> response.add(SessionDtoConverter.toDto(session)));
        return response;
    }

    @PostMapping(value = "/api/user/register", headers = { "content-type=application/json" })
    public UserDto register(@RequestBody UserDto userCredentials) throws ResponseStatusException {
        try {
            return UserDtoConverter.toDto(userDataService.addItem(UserDtoConverter.toModel(userCredentials)));
        }
        catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(value = "/api/user/login", headers = { "content-type=application/json" })
    public LogInResponseDto logIn(@RequestBody UserDto userCredentials) throws ResponseStatusException {
        Session startedSession;
        try {
            startedSession = userDataService.startSession(UserDtoConverter.toModel(userCredentials));
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        return new LogInResponseDto(startedSession.getId(),
                userDataService.getUserByNameAndPassword(userCredentials.getName(), userCredentials.getPassword()).isAdmin());
    }

    @DeleteMapping(value = "/api/user/logout", headers = { "content-type=application/json" })
    public void logOut(@RequestBody LogInResponseDto sessionKey) throws ResponseStatusException {
        validator.validateSessionKey(sessionKey.getSessionKey().toString());
        userDataService.endSession(sessionKey.getSessionKey());
    }
}
