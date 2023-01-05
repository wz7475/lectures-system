package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swizle.models.Opinion;
import swizle.models.User;
import swizle.models.dto.OpinionDto;
import swizle.services.interfaces.IOpinionDataService;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.dtoConverters.OpinionDtoConverter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OpinionController {
    private final IOpinionDataService opinionDataService;
    private final IUserDataService userDataService;

    @Autowired
    public OpinionController(
            @Qualifier(Constants.FakeOpinionServiceQualifier) IOpinionDataService opinionDataService,
            @Qualifier(Constants.UserServiceQualifier) IUserDataService userDataService) {
        this.opinionDataService = opinionDataService;
        this.userDataService = userDataService;
    }

    @GetMapping("api/opinions")
    public List<OpinionDto> getOpinions() {
        final ArrayList<OpinionDto> response = new ArrayList<>();
        opinionDataService.getItems().forEach(opinion -> response.add(OpinionDtoConverter.toDto(opinion)));
        return response;
    }

    @GetMapping("api/opinions/opinion")
    public OpinionDto getOpinionById(int id) {
        return OpinionDtoConverter.toDto(opinionDataService.getOpinionById(id));
    }

    @GetMapping("api/opinions/lecture")
    public List<OpinionDto> getOpinionsByLectureId(int lectureId) {
        final ArrayList<OpinionDto> response = new ArrayList<>();
        opinionDataService.getOpinionsByLectureId(lectureId).forEach(opinion -> response.add(OpinionDtoConverter.toDto(opinion)));
        return response;
    }

    @GetMapping("api/opinions/user")
    public List<OpinionDto> getOpinionsByUserId(int userId) {
        final ArrayList<OpinionDto> response = new ArrayList<>();
        opinionDataService.getOpinionsByUserId(userId).forEach(opinion -> response.add(OpinionDtoConverter.toDto(opinion)));
        return response;
    }

    @PostMapping(value = "api/opinions", headers = {"content-type=application/json"})
    public void postOpinion(@RequestBody OpinionDto opinion, String sessionKey) {
        User currentUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        opinionDataService.addItem(new Opinion(0, opinion.getLectureId(), currentUser.getId(),
                Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))), opinion.getContent()));
    }

    @PutMapping(value = "/api/opinions", headers = {"content-type=application/json"})
    public void putOpinion(int opinionId, @RequestBody OpinionDto opinion, String sessionKey) {
        User requestedUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        Opinion opinionToEdit = opinionDataService.getOpinionById(opinionId);
        if (requestedUser.getId() == opinionToEdit.getUserId() || requestedUser.isAdmin()) {
            opinionDataService.editItem(opinionId, new Opinion(opinionId, opinion.getLectureId(), opinionToEdit.getUserId(), opinionToEdit.getCreatedAt(), opinion.getContent()));
        }
    }

    @DeleteMapping("api/opinions")
    public void deleteOpinion(int opinionId, String sessionKey) {
        User requestedUser = userDataService.getUserBySessionKey(UUID.fromString(sessionKey));
        Opinion opinionToDelete = opinionDataService.getOpinionById(opinionId);
        if (requestedUser.getId() == opinionToDelete.getUserId() || requestedUser.isAdmin()) {
            opinionDataService.deleteItem(opinionId);
        }
    }
}