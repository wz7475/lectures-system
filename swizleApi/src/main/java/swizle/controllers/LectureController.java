package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swizle.models.Lecture;
import swizle.models.User;
import swizle.services.interfaces.ILectureDataService;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;

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
    public List<Lecture> getLectures() {
        return lectureDataService.getItems();
    }

    @GetMapping("api/lectures/lecture")
    public Lecture getLectureById(long id) {
        return lectureDataService.getItemById(id);
    }

    @GetMapping("api/lectures/user")
    public List<Lecture> getLecturesForUser(String sessionKey) {
        return lectureDataService.getLecturesForUser(
                userDataService.getUserBySessionKey(UUID.fromString(sessionKey)).getId()
        );
    }

    @PostMapping(value = "api/lectures", headers = {"content-type=application/json"})
    public void postLecture(@RequestBody Lecture lecture, String sessionKey) {
        if(isAdmin(sessionKey))
            lectureDataService.addItem(lecture);
    }

    @PutMapping(value = "/api/lectures", headers = {"content-type=application/json"})
    public void putLecture(long lectureId, @RequestBody Lecture lecture, String sessionKey) {
        User requestedUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));

        if(requestedUser.isAdmin())
            lectureDataService.editItem(lectureId, lecture);
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
