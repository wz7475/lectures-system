package swizle.services.interfaces;

import java.util.List;

public interface IDataService<T> {
    public List<T> getItems();
    public T getItemById(long id);
}
