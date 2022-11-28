package swizle.services;

import swizle.models.User;

public interface IUserDataService extends IModifiableDataService<User> {
    public void startSession(long userId);
    public void endSession(long userId);
}
