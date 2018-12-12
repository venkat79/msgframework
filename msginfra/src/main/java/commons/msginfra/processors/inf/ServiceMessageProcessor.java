package commons.msginfra.processors.inf;

import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.ServiceMessage;

import java.util.concurrent.Future;

public interface ServiceMessageProcessor<MESSAGE extends ServiceMessage> {

    Future<ServiceMessage> processMessage(MESSAGE message, AMQPMessageBundle amqpMessageBundle);
}

