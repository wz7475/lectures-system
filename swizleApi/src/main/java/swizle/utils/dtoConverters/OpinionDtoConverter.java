package swizle.utils.dtoConverters;

import swizle.models.Opinion;
import swizle.models.dto.OpinionDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class OpinionDtoConverter {
    public static Opinion toModel(OpinionDto opinionDto) {
        return new Opinion(
                opinionDto.getId(),
                opinionDto.getLectureId(),
                opinionDto.getUserId(),
                LocalDateTime.from(Instant.ofEpochMilli(opinionDto.getCreatedAt())),
                opinionDto.getContent()
        );
    }

    public static OpinionDto toDto(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                opinion.getLecture().getId(),
                opinion.getUser().getId(),
                opinion.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli(),
                opinion.getContent()
        );
    }
}
