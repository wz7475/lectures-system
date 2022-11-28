package swizle.services;

import java.util.List;

public interface IDataService<T> {
    public List<T> getItems();
    public T getItemById(long id);
}
