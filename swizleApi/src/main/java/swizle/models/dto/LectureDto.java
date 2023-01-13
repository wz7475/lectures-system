package swizle.models.dto;

public class LectureDto {
    private long id;
    private String name;
    private int dayOfWeek;
    private int beginTimeHour;
    private int beginTimeMinute;
    private int durationHours;
    private int durationMinutes;

    public LectureDto(long id, String name, int dayOfWeek, int beginTimeHour, int beginTimeMinute, int durationHours, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.beginTimeHour = beginTimeHour;
        this.beginTimeMinute = beginTimeMinute;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
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

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getBeginTimeHour() {
        return beginTimeHour;
    }

    public void setBeginTimeHour(int beginTimeHour) {
        this.beginTimeHour = beginTimeHour;
    }

    public int getBeginTimeMinute() {
        return beginTimeMinute;
    }

    public void setBeginTimeMinute(int beginTimeMinute) {
        this.beginTimeMinute = beginTimeMinute;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
