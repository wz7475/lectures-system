package swizle.services;

import swizle.models.Session;
import swizle.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserDataService extends IModifiableDataService<User> {
    public List<Session> getSessions();
    public User getUserByNameAndPassword(String name, String password);
    public User getUserBySessionKey(UUID sessionKey);
    public UUID startSession(User user);
    public void endSession(UUID sessionId);
}
