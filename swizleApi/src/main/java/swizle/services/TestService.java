package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.TestModel;

import java.util.*;

@Service("testService")
public class TestService implements IDataService<TestModel> {
    private final ArrayList<TestModel> models;

    public TestService() {
        models = new ArrayList<>();
    }

    @Override
    public void AddModel(TestModel model) {
        models.add(model);
    }

    @Override
    public List<TestModel> GetModels() {
        return models;
    }
}
