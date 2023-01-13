package swizle.services.interfaces;

public interface IModifiableDataService<T> extends IDataService<T> {
    public T addItem(T item);
    public void editItem(long id, T newData);
    public void deleteItem(long id);
}
