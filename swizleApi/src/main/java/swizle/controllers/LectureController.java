package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import swizle.models.Lecture;
import swizle.services.IModifiableDataService;
import swizle.utils.Constants;

@RestController
public class LectureController {
    @Qualifier(Constants.FakeLectureServiceQualifier)
    private IModifiableDataService<Lecture> lectureDataService;

    @Autowired
    public LectureController(IModifiableDataService<Lecture> lectureDataService) {
        this.lectureDataService = lectureDataService;
    }
}
