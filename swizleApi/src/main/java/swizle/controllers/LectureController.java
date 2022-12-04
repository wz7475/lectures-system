package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swizle.models.User;
import swizle.models.dto.LectureDto;
import swizle.services.interfaces.ILectureDataService;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.dtoConverters.LectureDtoConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class LectureController {
    @Qualifier(Constants.FakeLectureServiceQualifier)
    private final ILectureDataService lectureDataService;

    @Qualifier(Constants.FakeUserServiceQualifier)
    private final IUserDataService userDataService;

    @Autowired
    public LectureController(ILectureDataService lectureDataService, IUserDataService userDataService) {
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
        return LectureDtoConverter.toDto(lectureDataService.getItemById(id));
    }

    @GetMapping("api/lectures/user")
    public List<LectureDto> getLecturesForUser(String sessionKey) {
        final ArrayList<LectureDto> response = new ArrayList<>();
        lectureDataService.getLecturesForUser(
                userDataService.getUserBySessionKey(UUID.fromString(sessionKey)).getId()
        ).forEach(lecture -> response.add(LectureDtoConverter.toDto(lecture)));
        return response;
    }

    @PostMapping(value = "api/lectures", headers = {"content-type=application/json"})
    public void postLecture(@RequestBody LectureDto lecture, String sessionKey) {
        if(isAdmin(sessionKey))
            lectureDataService.addItem(LectureDtoConverter.toModel(lecture));
    }

    @PutMapping(value = "/api/lectures", headers = {"content-type=application/json"})
    public void putLecture(long lectureId, @RequestBody LectureDto lecture, String sessionKey) {
        User requestedUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));

        if(requestedUser.isAdmin())
            lectureDataService.editItem(lectureId, LectureDtoConverter.toModel(lecture));
    }

    @DeleteMapping("api/lectures")
    public void deleteLecture(long lectureId, String sessionKey) {
        if(isAdmin(sessionKey))
            lectureDataService.deleteItem(lectureId);
    }

    @PostMapping("api/lectures/signup")
    public void signUpForLecture(long lectureId, String sessionKey) {
        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        lectureDataService.signUpForLecture(lectureId, currentUser.getId());
    }

    @DeleteMapping ("api/lectures/optout")
    public void optOutFromLecture(long lectureId, String sessionKey) {
        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        lectureDataService.optOutOfLecture(lectureId, currentUser.getId());
    }

    private boolean isAdmin(String sessionKey) {
        return userDataService.getUserBySessionKey(UUID.fromString(sessionKey)).isAdmin();
    }
}
