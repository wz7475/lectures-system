package swizle.models;

import java.util.Date;

public class Opinion {
    private int id;
    private int lectureId;
    private int userId;
    private Date createdAt;
    private String content;

    public Opinion(int id, int lectureId, int userId, Date createdAt, String content) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
