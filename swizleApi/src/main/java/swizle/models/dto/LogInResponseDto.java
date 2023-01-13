package swizle.models.dto;

import java.util.UUID;

public class LogInResponseDto {
    private UUID sessionKey;
    private boolean isAdmin;
    private long userId;

    public LogInResponseDto() {

    }

    public LogInResponseDto(UUID sessionKey, boolean isAdmin) {
        this.sessionKey = sessionKey;
        this.isAdmin = isAdmin;
    }

    public LogInResponseDto(UUID sessionKey, boolean isAdmin, long userId) {
        this.sessionKey = sessionKey;
        this.isAdmin = isAdmin;
        this.userId = userId;
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

    public long getUserId() { return userId; }

    public void setUserId(long userId) { this.userId = userId; }
}
