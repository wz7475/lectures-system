package swizle.utils.dtoConverters;

import swizle.models.Lecture;
import swizle.models.dto.LectureDto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class LectureDtoConverter {
    public static Lecture toModel(LectureDto lectureDto) {
        return new Lecture(
                lectureDto.getId(),
                lectureDto.getName(),
                DayOfWeek.of(lectureDto.getDayOfWeek()),
                LocalTime.of(lectureDto.getBeginTimeHour(), lectureDto.getBeginTimeMinute()),
                Duration.ofHours(lectureDto.getDurationHours())
                        .plus(Duration.ofMinutes(lectureDto.getDurationMinutes()))
        );
    }

    public static LectureDto toDto(Lecture lecture) {
        return new LectureDto(
                lecture.getId(),
                lecture.getName(),
                lecture.getDayOfWeek().getValue(),
                lecture.getBeginTime().getHour(),
                lecture.getBeginTime().getMinute(),
                lecture.getDuration().toHoursPart(),
                lecture.getDuration().toMinutesPart()
        );
    }
}
