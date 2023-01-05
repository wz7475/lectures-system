package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.Lecture;
import swizle.models.User;
import swizle.models.dto.LectureDto;
import swizle.services.interfaces.ILectureDataService;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.dtoConverters.LectureDtoConverter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class LectureController {
    private final ILectureDataService lectureDataService;
    private final IUserDataService userDataService;

    @Autowired
    public LectureController(
            @Qualifier(Constants.FakeLectureServiceQualifier) ILectureDataService lectureDataService,
            @Qualifier(Constants.UserServiceQualifier) IUserDataService userDataService) {
        this.lectureDataService = lectureDataService;
        this.userDataService = userDataService;
    }

    @GetMapping("api/lectures")
    public List<LectureDto> getLectures() {
        final ArrayList<LectureDto> response = new ArrayList<>();
        lectureDataService.getItems().forEach(lecture -> response.add(LectureDtoConverter.toDto(lecture)));
        return response;
    }

    @GetMapping("api/lectures/lecture")
    public LectureDto getLectureById(long id) {
        Lecture requestedLecture = lectureDataService.getItemById(id);

        if(requestedLecture == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested lecture does not exist.");

        return LectureDtoConverter.toDto(requestedLecture);
    }

    @GetMapping("api/lectures/user")
    public List<LectureDto> getLecturesForUser(String sessionKey) {
        try {
            final ArrayList<LectureDto> response = new ArrayList<>();
            lectureDataService.getLecturesForUser(
                    userDataService.getUserBySessionKey(UUID.fromString(sessionKey)).getId()
            ).forEach(lecture -> response.add(LectureDtoConverter.toDto(lecture)));
            return response;
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "api/lectures", headers = { "content-type=application/json" })
    public LectureDto postLecture(@RequestBody LectureDto lecture, String sessionKey) {
        if(isAdmin(sessionKey)) {
            try {
                return LectureDtoConverter.toDto(
                        lectureDataService.addItem(LectureDtoConverter.toModel(lecture))
                );
            }
            catch(IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only admin can add lectures.");
    }

    @PutMapping(value = "/api/lectures", headers = { "content-type=application/json" })
    public void putLecture(long lectureId, @RequestBody LectureDto lecture, String sessionKey) throws ResponseStatusException {
        User requestedUser;

        try {
            requestedUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        if(requestedUser == null)
            throwInvalidSessionKey();

        if(requestedUser.isAdmin()) {
            try {
                lectureDataService.editItem(lectureId, LectureDtoConverter.toModel(lecture));
            }
            catch (InvalidParameterException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
            catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has insufficient privileges to perform this action.");
        }
    }

    @DeleteMapping("api/lectures")
    public void deleteLecture(long lectureId, String sessionKey) {
        if (isAdmin(sessionKey)) {
            try {
                lectureDataService.deleteItem(lectureId);
            }
            catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has insufficient privileges to perform this action.");
    }

    @PostMapping("api/lectures/signup")
    public void signUpForLecture(long lectureId, String sessionKey) {
        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));

        if(currentUser == null)
            throwInvalidSessionKey();

        try {
            lectureDataService.signUpForLecture(lectureId, currentUser.getId());
        }
        catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping ("api/lectures/optout")
    public void optOutFromLecture(long lectureId, String sessionKey) {
        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));

        if(currentUser == null)
            throwInvalidSessionKey();

        try {
            lectureDataService.optOutOfLecture(lectureId, currentUser.getId());
        }
        catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/api/lectures/recent")
    public List<Long> getRecentLectures(long lectureId) {
        List<Long> result = new ArrayList<>();
        List<Lecture> lectures = lectureDataService.getItems();
        lectures.forEach(lecture -> {
            if (lecture.getId() > lectureId)
                result.add(lecture.getId());
        });

        return result;
    }

    private boolean isAdmin(String sessionKey) throws NullPointerException {
        User user =  userDataService.getUserBySessionKey(UUID.fromString(sessionKey));

        if(user == null)
            throwInvalidSessionKey();

        return user.isAdmin();
    }

    private void throwInvalidSessionKey() throws ResponseStatusException {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session with the given key does not exist.");
    }
}
