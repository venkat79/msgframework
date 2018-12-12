package commons.msgbusinitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;


@SpringBootApplication
@ImportResource({"classpath:config/msgbus-initializer.xml"})
public class MsgbusinitializerApplication {

    private static final Logger _logger = LoggerFactory.getLogger(MsgbusinitializerApplication.class);

    @Autowired
    private SimpleMessageListenerContainer rabbitListenerContainer;

    @Autowired
    private SimpleMessageListenerContainer rabbitListenerContainerSync;

    public static void main(String[] args) {
        SpringApplication.run(MsgbusinitializerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doListenerCleanup() {

        _logger.debug("Closing down all listener containers...");
        rabbitListenerContainer.stop();
        rabbitListenerContainerSync.stop();
    }
}
