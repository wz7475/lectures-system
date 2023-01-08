package swizle.utils.dtoConverters;

import swizle.models.Session;
import swizle.models.dto.SessionDto;

public class SessionDtoConverter {
    public static SessionDto toDto(Session session) {
        return new SessionDto(session.getId(), session.getUser().getId());
    }
}
