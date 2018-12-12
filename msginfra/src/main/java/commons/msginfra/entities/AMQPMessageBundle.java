package commons.msginfra.entities;

import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;

public class AMQPMessageBundle {

    private Message message;
    private Channel channel;


    public AMQPMessageBundle(Message message, Channel channel) {
        this.message = message;
        this.channel = channel;
    }

    public Message getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }
}
