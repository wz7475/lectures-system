package swizle.utils.dtoConverters;

import swizle.models.Opinion;
import swizle.models.dto.OpinionDto;

import java.time.Instant;
import java.util.Date;

public class OpinionDtoConverter {
    public static Opinion toModel(OpinionDto opinionDto) {
        return new Opinion(
                opinionDto.getId(),
                opinionDto.getLectureId(),
                opinionDto.getUserId(),
                Date.from(Instant.ofEpochMilli(opinionDto.getCreatedAt())),
                opinionDto.getContent()
        );
    }

    public static OpinionDto toDto(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                opinion.getLectureId(),
                opinion.getUserId(),
                opinion.getCreatedAt().toInstant().toEpochMilli(),
                opinion.getContent()
        );
    }
}
