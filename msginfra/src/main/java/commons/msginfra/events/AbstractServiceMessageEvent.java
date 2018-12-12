package commons.msginfra.events;

import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.ServiceMessage;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractServiceMessageEvent<MESSAGE extends ServiceMessage>
        extends ApplicationEvent implements ServiceMessageEvent<MESSAGE> {

    private MESSAGE message;
    private AMQPMessageBundle amqpMessageBundle;

    public AbstractServiceMessageEvent() {
        super(new Object());
    }

    public AbstractServiceMessageEvent(Object source) {
        super(source);

    }

    public AbstractServiceMessageEvent(Object source, MESSAGE message, AMQPMessageBundle amqpMessageBundle){
        this(source);
        this.message = message;
        this.amqpMessageBundle = amqpMessageBundle;
    }

    @Override
    public MESSAGE getMessage() {
        return message;
    }

    @Override
    public AMQPMessageBundle getAMQPMessageBundle() {
        return amqpMessageBundle;
    }
}
