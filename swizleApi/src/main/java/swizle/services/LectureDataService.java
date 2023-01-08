package swizle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swizle.models.Lecture;
import swizle.models.User;
import swizle.repositories.ILectureRepository;
import swizle.repositories.IUserRepository;
import swizle.services.interfaces.ILectureDataService;
import swizle.utils.Constants;

import java.security.InvalidParameterException;
import java.util.List;

@Service(Constants.LectureServiceQualifier)
public class LectureDataService implements ILectureDataService {
    private final ILectureRepository lectureRepository;
    private final IUserRepository userRepository;

    @Autowired
    public LectureDataService(ILectureRepository lectureRepository, IUserRepository userRepository) {
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;

    }

    @Override
    public List<Lecture> getItems() {
        return lectureRepository.findAll();
    }

    @Override
    public Lecture getItemById(long id) {
        if(!lectureRepository.existsById(id))
            return null;

        return lectureRepository.getReferenceById(id);
    }

    @Override
    public List<Lecture> getLecturesForUser(long userId) {
        return userRepository.getReferenceById(userId).getLectures().stream().toList();
    }

    @Override
    public void signUpForLecture(long lectureId, long userId) {
        validateUserAndLectureId(userId, lectureId);

        User requestedUser = userRepository.getReferenceById(userId);
        Lecture requestedLecture = lectureRepository.getReferenceById(lectureId);

        if(requestedLecture.getUsers().contains(requestedUser))
            throw new IllegalArgumentException("User already signed up for requested lecture.");

        requestedLecture.addUser(requestedUser);
        lectureRepository.save(requestedLecture);
    }

    @Override
    public void optOutOfLecture(long lectureId, long userId) {
        validateUserAndLectureId(userId, lectureId);

        User requestedUser = userRepository.getReferenceById(userId);
        Lecture requestedLecture = lectureRepository.getReferenceById(lectureId);

        if(!requestedLecture.getUsers().contains(requestedUser))
            throw new IllegalArgumentException("User is not assigned to the requested lecture.");

        requestedLecture.removeUser(requestedUser);
        lectureRepository.save(requestedLecture);
    }

    @Override
    public Lecture addItem(Lecture item) {
        return null;
    }

    @Override
    public void editItem(long id, Lecture newData) {

    }

    @Override
    public void deleteItem(long id) {

    }

    private void validateUserAndLectureId(long userId, long lectureId) {
        if(!lectureRepository.existsById(lectureId))
            throw new InvalidParameterException("Requested lecture does not exist.");
        if(!userRepository.existsById(userId))
            throw new InvalidParameterException(("Requested user does not exist."));
    }
}
