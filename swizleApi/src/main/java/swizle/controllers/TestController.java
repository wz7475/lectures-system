package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import swizle.models.TestModel;
import swizle.services.TestService;

import java.util.List;

@RestController
public class TestController {
    @Qualifier("testService")
    private final TestService service;

    @Autowired
    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/api/test")
    public List<TestModel> getModels() {
        return service.GetModels();
    }

    @PostMapping("/api/test")
    public void addModelWithTest(String text) {
        service.AddModel(new TestModel(service.GetModels().size(), text));
    }
}
