package commons.msginfra.events;

import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.ServiceMessage;

public interface ServiceMessageEvent<MESSAGE extends ServiceMessage> {

    MESSAGE getMessage();

    AMQPMessageBundle getAMQPMessageBundle();
}
