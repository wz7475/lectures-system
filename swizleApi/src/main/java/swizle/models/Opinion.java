package swizle.models;

import java.util.Date;

public class Opinion implements IModel {
    private long id;
    private long lectureId;
    private long userId;
    private Date createdAt;
    private String content;

    public Opinion(long id, long lectureId, long userId, Date createdAt, String content) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
