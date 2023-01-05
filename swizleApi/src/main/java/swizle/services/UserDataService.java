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

    @Autowired
    public UserDataService(IUserRepository repository) {
        userRepository = repository;
    }

    @Override
    public List<User> getItems() {
        return userRepository.findAll();
    }

    @Override
    public User getItemById(long id) {
        return null;
    }

    @Override
    public User addItem(User item) {
        return null;
    }

    @Override
    public void editItem(long id, User newData) {

    }

    @Override
    public void deleteItem(long id) {

    }

    @Override
    public List<Session> getSessions() {
        return null;
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        return null;
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
