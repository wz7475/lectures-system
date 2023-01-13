package swizle.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RoutineManager {
    @Scheduled(cron = "*/1 * * * * *")
    public void reportTask() {
        System.out.println("hello!");
    }
}
