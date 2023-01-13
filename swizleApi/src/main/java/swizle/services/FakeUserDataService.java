package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.Session;
import swizle.models.User;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(Constants.FakeUserServiceQualifier)
public class FakeUserDataService implements IUserDataService {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Session> sessions = new ArrayList<>();

    public FakeUserDataService() {
        users.add(new User(1, "admin", "12345", true));
        users.add(new User(2, "user", "user", false));
    }

    @Override
    public List<User> getItems() {
        return users;
    }

    @Override
    public User getItemById(long id) {
        for(User user : users) {
            if(user.getId() == id)
                return user;
        }

        return null;
    }

    @Override
    public User addItem(User item) throws IllegalArgumentException {
        for(User user : users) {
            if(user.getName().equals(item.getName()))
                throw new IllegalArgumentException("User with the given name already exists.");
        }

        User userToAdd = new User(Helpers.getUniqueId(users), item.getName(), item.getPassword());
        users.add(userToAdd);
        return userToAdd;
    }

    @Override
    public void editItem(long id, User newData) throws IllegalArgumentException {
        User itemToModify = getItemById(id);

        if(itemToModify == null)
            throw new IllegalArgumentException("User with given id doesn't exist.");

        itemToModify.setName(newData.getName());
        itemToModify.setPassword(newData.getPassword());
        itemToModify.setAdmin(newData.isAdmin());
    }

    @Override
    public void deleteItem(long id) {
        users.removeIf(u -> u.getId() == id);
    }

    @Override
    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        for(User user : users) {
            if(user.getName().equals(name) && user.getPassword().equals(password))
                return user;
        }

        return null;
    }

    @Override
    public User getUserBySessionKey(UUID sessionKey) {
        for(Session session : sessions) {
            if(session.getId().equals(sessionKey))
                return getItemById(session.getUserId());
        }

        return null;
    }

    @Override
    public Session startSession(User user) throws IllegalArgumentException {
        User requestedUser = getUserByNameAndPassword(user.getName(), user.getPassword());
        if(requestedUser == null)
            throw new IllegalArgumentException("Invalid user credentials.");

        Session newSession = new Session(requestedUser.getId());
        sessions.add(newSession);
        return newSession;
    }

    @Override
    public void endSession(UUID sessionId) {
        Session session = null;

        for(Session s : sessions) {
            if(s.getId().equals(sessionId))
                session = s;
        }

        if(session == null)
            throw new IllegalArgumentException("Session with the given key doesn't exist.");

        sessions.remove(session);
    }
}
