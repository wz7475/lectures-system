package swizle.services;

public interface IModifiableDataService<T> extends IDataService<T> {
    public void addItem(T item);
    public void editItem(long id, T newData);
    public void deleteItem(long id);
}
