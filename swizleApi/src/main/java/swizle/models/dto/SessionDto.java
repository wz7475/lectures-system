package swizle.models.dto;

import java.util.UUID;

public class SessionDto {
    private UUID id;
    private long userId;

    public SessionDto() { }
    public SessionDto(UUID id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
