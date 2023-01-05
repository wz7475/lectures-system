package swizle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swizle.models.Session;
import swizle.models.User;
import swizle.repositories.IUserRepository;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;

import java.util.List;
import java.util.UUID;

@Service(Constants.UserServiceQualifier)
public class UserDataService implements IUserDataService {
    private final IUserRepository userRepository;
    //private final ISessionRepository sessionRepository;

    @Autowired
    public UserDataService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        //this.sessionRepository = sessionRepository;
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
        userToModify.setPassword(newData.getPassword());
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
        return null;
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    @Override
    public User getUserBySessionKey(UUID sessionKey) {
        return null;
    }

    @Override
    public Session startSession(User user) {
        return null;
    }

    @Override
    public void endSession(UUID sessionId) {

    }
}
