package consumer.x2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:config/listener-async-queue.xml", "classpath:config/listener-sync-queue.xml",
                "classpath:config/exchange-queue-definition.xml", "config/x2-consumer-config.xml"})
public class X2Application {

    public static void main(String[] args) {
        SpringApplication.run(X2Application.class, args);
    }
}
