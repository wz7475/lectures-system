package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.User;

import java.util.ArrayList;
import java.util.List;

@Service("fakeUserService")
public class FakeUserDataService implements IDataService<User> {
    private final ArrayList<User> users = new ArrayList<>();

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
}
