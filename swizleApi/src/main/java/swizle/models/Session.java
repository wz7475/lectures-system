package swizle.models;

import java.util.UUID;

public class Session {
    private UUID id;
    private long userId;

    public Session(long userId) {
        id = UUID.randomUUID();
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }
}
