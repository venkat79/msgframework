package producer.x1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:config/producer-infra.xml"})
public class X1Application {

    public static void main(String[] args) {
        SpringApplication.run(X1Application.class, args);
    }
}
