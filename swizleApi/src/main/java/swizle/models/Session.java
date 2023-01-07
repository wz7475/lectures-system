package swizle.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Sessions")
public class Session {

    @Id
    @GeneratedValue
    private UUID id;

    @Transient
    private long userId;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public Session() {}

    public Session(long userId) {
        id = UUID.randomUUID();
        this.userId = userId;
    }

    public Session(User user) {
        id = UUID.randomUUID();
        this.user = user;
        userId = user.getId();
    }

    public UUID getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
