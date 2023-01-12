package swizle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swizle.models.Session;
import swizle.models.User;
import swizle.repositories.ISessionRepository;
import swizle.repositories.IUserRepository;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.PasswordCrypto;

import java.util.List;
import java.util.UUID;

@Service(Constants.UserServiceQualifier)
public class UserDataService implements IUserDataService {
    private final IUserRepository userRepository;
    private final ISessionRepository sessionRepository;
    private final PasswordCrypto passwordCrypto;

    @Autowired
    public UserDataService(IUserRepository userRepository, ISessionRepository sessionRepository,
                           PasswordCrypto passwordCrypto) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordCrypto = passwordCrypto;
    }

    @Override
    public List<User> getItems() {
        return userRepository.findAll();
    }

    @Override
    public User getItemById(long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User addItem(User item) {
        try {
            item.setPassword(passwordCrypto.encryptPassword(item.getPassword()));
            return userRepository.save(item);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("User with the given name already exists.");
        }
    }

    @Override
    public void editItem(long id, User newData) {
        User userToModify = userRepository.getReferenceById(id);

        userToModify.setName(newData.getName());
        userToModify.setPassword(passwordCrypto.encryptPassword(newData.getPassword()));
        userToModify.setAdmin(newData.isAdmin());

        try {
            userRepository.save(userToModify);
        }
        catch(Exception e) {
            throw new IllegalArgumentException("User with given name already exists");
        }
    }

    @Override
    public void deleteItem(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        for(User user : userRepository.findAll()) {
            if(user.getName().equals(name) && passwordCrypto.passwordMatches(password, user.getPassword()))
                return user;
        }

        return null;
    }

    @Override
    public User getUserBySessionKey(UUID sessionKey) {
        if(!sessionRepository.existsById(sessionKey))
            return null;

        return sessionRepository.getReferenceById(sessionKey).getUser();
    }

    @Override
    public Session startSession(User user) {
        User requestedUser = getUserByNameAndPassword(user.getName(), user.getPassword());

        if(requestedUser == null)
            throw new IllegalArgumentException("Invalid user credentials.");

        return sessionRepository.save(new Session(requestedUser));
    }

    @Override
    public void endSession(UUID sessionId) {
        if (!sessionRepository.existsById(sessionId))
            throw new IllegalArgumentException("Session with the given key doesn't exist.");

        Session session = sessionRepository.getReferenceById(sessionId);
        sessionRepository.delete(session);
    }
}
