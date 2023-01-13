package swizle.models;

public class UserLecture implements IModel {
    private long id;
    private long userId;
    private long lectureId;

    public UserLecture(long id, long userId, long lectureId) {
        this.id = id;
        this.userId = userId;
        this.lectureId = lectureId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }
}
