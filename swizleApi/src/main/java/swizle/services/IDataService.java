package swizle.services;

import java.util.List;

public interface IDataService<T> {
    public List<T> GetModels();
    public void AddModel(T model);
}
