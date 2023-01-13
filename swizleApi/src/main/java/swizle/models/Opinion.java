package swizle.models;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "Opinions")
public class Opinion implements IModel {
    @Id
    @SequenceGenerator(name = "opinion_id_sequence", sequenceName = "opinion_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opinion_id_sequence")
    private long id;

    @Transient
    private long lectureId;
    @Transient
    private long userId;
    private LocalDateTime createdAt;
    private String content;

    @ManyToOne
    private Lecture lecture;
    @ManyToOne
    private User user;

    public Opinion() { }

    public Opinion(long id, long lectureId, long userId, LocalDateTime createdAt, String content) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Opinion(Lecture lecture, User user, LocalDateTime createdAt, String content) {
        this.lecture = lecture;
        this.user = user;
        this.createdAt = createdAt;
        this.content = content;
        this.userId = user.getId();
        this.lectureId = lecture.getId();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
