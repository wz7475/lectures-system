package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.Lecture;
import swizle.models.UserLecture;
import swizle.services.interfaces.ILectureDataService;
import swizle.utils.Constants;
import swizle.utils.Helpers;

import java.util.ArrayList;
import java.util.List;

@Service(Constants.FakeLectureServiceQualifier)
public class FakeLectureDataService implements ILectureDataService {
    private final ArrayList<Lecture> lectures = new ArrayList<>();
    private final ArrayList<UserLecture> userLectures = new ArrayList<>();

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
    public void addItem(Lecture item) {
        lectures.add(new Lecture(Helpers.getUniqueId(lectures), item.getName(), item.getDayOfWeek(), item.getBeginTime(), item.getDuration()));
    }

    @Override
    public void editItem(long id, Lecture newData) {
        Lecture lectureToEdit = getItemById(id);

        lectureToEdit.setName(newData.getName());
        lectureToEdit.setBeginTime(newData.getBeginTime());
        lectureToEdit.setDayOfWeek(newData.getDayOfWeek());
        lectureToEdit.setDuration(newData.getDuration());
    }

    @Override
    public void deleteItem(long id) {
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
        for(UserLecture userLecture : userLectures) {
            if(userLecture.getUserId() == lectureId && userLecture.getLectureId() == lectureId)
                throw new IllegalArgumentException("User already signed up for requested lecture.");
        }

        userLectures.add(new UserLecture(Helpers.getUniqueId(userLectures), userId, lectureId));
    }

    @Override
    public void optOutOfLecture(long lectureId, long userId) {
        userLectures.removeIf(userLecture -> userLecture.getLectureId() == lectureId && userLecture.getUserId() == userId);
    }
}
