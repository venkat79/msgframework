package commons.msginfra.listeners.inf;

import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.ServiceMessage;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * Interface provides the main entry point of all ServiceMessage received
 * on a Consumer asynchronously
 *
 * @param <MESSAGE>
 */
public interface ServiceMessageListener<MESSAGE extends ServiceMessage> extends ChannelAwareMessageListener {

    ServiceMessage handleMessage(MESSAGE message, AMQPMessageBundle amqpMessageBundle);
}
