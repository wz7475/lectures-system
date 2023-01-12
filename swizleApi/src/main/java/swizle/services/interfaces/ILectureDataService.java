package swizle.services.interfaces;

import swizle.models.Lecture;
import swizle.models.User;

import java.util.List;

public interface ILectureDataService extends IModifiableDataService<Lecture> {
    public List<Lecture> getLecturesForUser(long userId);
    public void signUpForLecture(long lectureId, long userId);
    public void optOutOfLecture(long lectureId, long userId);

    public List<Long> getSignedUsersIdForLecture(long lectureId);
}
