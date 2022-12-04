package swizle.models.dto;

public class LectureDto {
    private long id;
    private String name;
    private byte dayOfWeek;
    private byte beginTimeHour;
    private byte beginTimeMinute;
    private byte durationHours;
    private byte durationMinutes;

    public LectureDto(long id, String name, byte dayOfWeek, byte beginTimeHour, byte beginTimeMinute, byte durationHours, byte durationMinutes) {
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

    public byte getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(byte dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public byte getBeginTimeHour() {
        return beginTimeHour;
    }

    public void setBeginTimeHour(byte beginTimeHour) {
        this.beginTimeHour = beginTimeHour;
    }

    public byte getBeginTimeMinute() {
        return beginTimeMinute;
    }

    public void setBeginTimeMinute(byte beginTimeMinute) {
        this.beginTimeMinute = beginTimeMinute;
    }

    public byte getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(byte durationHours) {
        this.durationHours = durationHours;
    }

    public byte getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(byte durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
