package swizle.models.dto;

public class OpinionDto {
    private long id;
    private long lectureId;
    private long userId;
    private long createdAt;
    private String content;

    public OpinionDto(long id, long lectureId, long userId, long createdAt, String content) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
