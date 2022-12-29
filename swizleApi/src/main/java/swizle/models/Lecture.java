package swizle.models;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class Lecture implements IModel {
    private long id;
    private String name;
    private DayOfWeek dayOfWeek;
    private LocalTime beginTime;
    private Duration duration;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(name, lecture.name) && dayOfWeek == lecture.dayOfWeek && Objects.equals(beginTime, lecture.beginTime) && Objects.equals(duration, lecture.duration);
    }
}
