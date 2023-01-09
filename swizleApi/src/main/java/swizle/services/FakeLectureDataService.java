package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.Lecture;
import swizle.models.UserLecture;
import swizle.services.interfaces.ILectureDataService;
import swizle.utils.Constants;
import swizle.utils.Helpers;

import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service(Constants.FakeLectureServiceQualifier)
public class FakeLectureDataService implements ILectureDataService {
    private final ArrayList<Lecture> lectures = new ArrayList<>();
    private final ArrayList<UserLecture> userLectures = new ArrayList<>();

    public FakeLectureDataService() {
        lectures.add(new Lecture(
                1,
                "English",
                DayOfWeek.MONDAY,
                LocalTime.of(12, 15),
                Duration.ofHours(1))
        );
        lectures.add(new Lecture(
                2,
                "German",
                DayOfWeek.FRIDAY,
                LocalTime.of(15, 30),
                Duration.ofHours(2))
        );
    }

    @Override
    public List<Lecture> getItems() {
        return lectures;
    }

    @Override
    public Lecture getItemById(long id) {
        for(Lecture lecture : lectures) {
            if(lecture.getId() == id)
                return lecture;
        }

        return null;
    }

    @Override
    public Lecture addItem(Lecture item) {
        validateNewLecture(item);
        Lecture lectureToAdd = new Lecture(
                Helpers.getUniqueId(lectures),
                item.getName(),
                item.getDayOfWeek(),
                item.getBeginTime(),
                item.getDuration()
        );

        lectures.add(lectureToAdd);
        return lectureToAdd;
    }

    @Override
    public void editItem(long id, Lecture newData) {
        validateLectureId(id);
        validateNewLecture(newData);
        Lecture lectureToEdit = getItemById(id);

        lectureToEdit.setName(newData.getName());
        lectureToEdit.setBeginTime(newData.getBeginTime());
        lectureToEdit.setDayOfWeek(newData.getDayOfWeek());
        lectureToEdit.setDuration(newData.getDuration());
    }

    @Override
    public void deleteItem(long id) {
        validateLectureId(id);
        lectures.removeIf(lecture -> lecture.getId() == id);
    }

    @Override
    public List<Lecture> getLecturesForUser(long userId) {
        ArrayList<Lecture> result = new ArrayList<>();
        userLectures.forEach(userLecture -> {
            if(userLecture.getUserId() == userId)
                result.add(getItemById(userLecture.getLectureId()));
        });

        return result;
    }

    @Override
    public void signUpForLecture(long lectureId, long userId) throws IllegalArgumentException {
        validateLectureId(lectureId);

        for(UserLecture userLecture : userLectures) {
            if(userLecture.getUserId() == userId && userLecture.getLectureId() == lectureId)
                throw new IllegalArgumentException("User already signed up for requested lecture.");
        }

        userLectures.add(new UserLecture(Helpers.getUniqueId(userLectures), userId, lectureId));
    }

    @Override
    public void optOutOfLecture(long lectureId, long userId) {
        validateLectureId(lectureId);

        boolean assignmentExists = false;
        for(UserLecture userLecture : userLectures) {
            if(userLecture.getLectureId() == lectureId && userLecture.getUserId() == userId) {
                assignmentExists = true;
                break;
            }
        }

        if(!assignmentExists)
            throw new IllegalArgumentException("User is not assigned to the requested lecture.");

        userLectures.removeIf(userLecture -> userLecture.getLectureId() == lectureId && userLecture.getUserId() == userId);
    }

    private void validateLectureId(long lectureId) throws InvalidParameterException {
        boolean lectureExists = false;

        for(Lecture lecture : lectures) {
            if(lecture.getId() == lectureId) {
                lectureExists = true;
                break;
            }
        }

        if(!lectureExists)
            throw new InvalidParameterException("Requested lecture does not exist.");
    }

    private void validateNewLecture(Lecture item) throws IllegalArgumentException {
        for(Lecture lecture : lectures) {
            if(lecture.equals(item))
                throw new IllegalArgumentException("Lecture containing requested data already exists.");
        }
    }
}
