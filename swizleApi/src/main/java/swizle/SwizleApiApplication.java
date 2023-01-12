package swizle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SwizleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwizleApiApplication.class, args);
    }

}
