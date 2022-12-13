package swizle.models.dto;

import java.util.UUID;

public class SessionResponseDto {
    private UUID sessionKey;
    private boolean isAdmin;

    public SessionResponseDto(UUID sessionKey, boolean isAdmin) {
        this.sessionKey = sessionKey;
        this.isAdmin = isAdmin;
    }

    public UUID getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(UUID sessionKey) {
        this.sessionKey = sessionKey;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
