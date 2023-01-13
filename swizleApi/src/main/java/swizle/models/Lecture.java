package swizle.models;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Lectures")
public class Lecture implements IModel {
    @Id
    @SequenceGenerator(name = "lecture_id_sequence", sequenceName = "lecture_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lecture_id_sequence")
    private long id;
    private String name;
    private DayOfWeek dayOfWeek;
    private LocalTime beginTime;

    private Duration duration;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "lectures_users",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    private Set<User> users = new HashSet<>();

    public Lecture() { }

    public Lecture(long id, String name, DayOfWeek dayOfWeek, LocalTime beginTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.beginTime = beginTime;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
        user.getLectures().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getLectures().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(name, lecture.name) && dayOfWeek == lecture.dayOfWeek && Objects.equals(beginTime, lecture.beginTime) && Objects.equals(duration, lecture.duration);
    }
}
