package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.Lecture;
import swizle.models.Opinion;
import swizle.models.User;
import swizle.models.dto.OpinionDto;
import swizle.services.interfaces.ILectureDataService;
import swizle.services.interfaces.IOpinionDataService;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.Validator;
import swizle.utils.dtoConverters.OpinionDtoConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OpinionController {
    private final IOpinionDataService opinionDataService;
    private final IUserDataService userDataService;
    private final ILectureDataService lectureDataService;
    private Validator validator;

    @Autowired
    public OpinionController(
            @Qualifier(Constants.OpinionServiceQualifier) IOpinionDataService opinionDataService,
            @Qualifier(Constants.UserServiceQualifier) IUserDataService userDataService,
            @Qualifier(Constants.LectureServiceQualifier) ILectureDataService lectureDataService,
            Validator validator) {
        this.opinionDataService = opinionDataService;
        this.userDataService = userDataService;
        this.lectureDataService = lectureDataService;
        this.validator = validator;
    }

    @GetMapping("api/opinions")
    public List<OpinionDto> getOpinions() {
        final ArrayList<OpinionDto> response = new ArrayList<>();
        opinionDataService.getItems().forEach(opinion -> response.add(OpinionDtoConverter.toDto(opinion)));
        return response;
    }

    @GetMapping("api/opinions/opinion")
    public OpinionDto getOpinionById(int id) {
        Opinion requestedOpinion = opinionDataService.getItemById(id);
        validateOpinion(requestedOpinion);
        return OpinionDtoConverter.toDto(requestedOpinion);
    }

    @GetMapping("api/opinions/lecture")
    public List<OpinionDto> getOpinionsByLectureId(int lectureId) {
        final ArrayList<OpinionDto> response = new ArrayList<>();
        opinionDataService.getOpinionsByLectureId(lectureId).forEach(opinion -> response.add(OpinionDtoConverter.toDto(opinion)));
        return response;
    }

    @GetMapping("api/opinions/user")
    public List<OpinionDto> getOpinionsByUserId(String sessionKey) {
        validator.validateSessionKey(sessionKey);
        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        final ArrayList<OpinionDto> response = new ArrayList<>();
        opinionDataService.getOpinionsByUserId(currentUser.getId()).forEach(opinion -> response.add(OpinionDtoConverter.toDto(opinion)));
        return response;
    }

    @PostMapping(value = "api/opinions", headers = {"content-type=application/json"})
    public void postOpinion(@RequestBody OpinionDto opinion, String sessionKey) {
        validator.validateSessionKey(sessionKey);

        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        Lecture requestedLecture = lectureDataService.getItemById(opinion.getLectureId());
        validateLecture(requestedLecture);

        opinionDataService.addItem(new Opinion(requestedLecture, currentUser,
                LocalDateTime.now(), opinion.getContent()));
    }

    @PutMapping(value = "/api/opinions", headers = {"content-type=application/json"})
    public void putOpinion(int opinionId, @RequestBody OpinionDto opinion, String sessionKey) {
        validator.validateSessionKey(sessionKey);

        User requestedUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        Lecture newLecture = lectureDataService.getItemById(opinion.getLectureId());
        Opinion opinionToEdit = opinionDataService.getItemById(opinionId);

        validateLecture(newLecture);
        validateOpinion(opinionToEdit);

        opinionDataService.editItem(opinionId, new Opinion(newLecture, requestedUser, opinionToEdit.getCreatedAt(), opinion.getContent()));
    }

    @DeleteMapping("api/opinions")
    public void deleteOpinion(int opinionId, String sessionKey) {
        validator.validateSessionKey(sessionKey);
        Opinion opinionToDelete = opinionDataService.getItemById(opinionId);
        validateOpinion(opinionToDelete);
        opinionDataService.deleteItem(opinionId);
    }

    private void validateLecture(Lecture lecture) {
        if(lecture == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested lecture does not exist.");
    }

    private void validateOpinion(Opinion opinion) {
        if(opinion == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested opinion does not exist.");
    }
}