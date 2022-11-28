package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.User;

import java.util.ArrayList;
import java.util.List;

@Service("fakeUserService")
public class FakeUserDataService implements IModifiableDataService<User> {
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

    @Override
    public void addItem(User item) {
        users.add(new User(getUniqueId(), item.getName(), item.getPassword(), item.isAdmin()));
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

    private long getUniqueId() {
        return users.size() + 1;
    }
}
