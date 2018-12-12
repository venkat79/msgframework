package commons.msginfra.events;

import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.ServiceMessage;

public interface ServiceMessageEventGenerator< EVENT , MESSAGE extends ServiceMessage> {

    EVENT generateEvent(MESSAGE message, AMQPMessageBundle amqpMessageBundle);

}
