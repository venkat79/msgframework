package commons.msginfra.producers.impl;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

public abstract class AbstractSpringAMQPAdmin {

    protected AmqpAdmin amqpAdmin;

    public AbstractSpringAMQPAdmin() {
        super();
    }

    public abstract void setAmqpAdmin(AmqpAdmin amqpAdmin);

    /**
     * Create/Declare a 'DirectExchange' with defaults
     *
     * @param exchangeName
     */
    protected void declareDirectExchange(String exchangeName) {
        amqpAdmin.declareExchange(getDirectExchange(exchangeName));
    }

    protected void declareDirectExchange(String exchangeName, boolean durable) {
        amqpAdmin.declareExchange(getDirectExchange(exchangeName));
    }

    /**
     * Binds the exchange to queue with the specified routing key.
     *
     * @param exchange
     * @param queue
     * @param routingKey
     */
    protected void declareBinding(DirectExchange exchange, Queue queue, String routingKey) {
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
    }

    protected DirectExchange getDirectExchange(String exchangeName) {
        return new DirectExchange(exchangeName);
    }

    /**
     * Declares a temporary queue, the queue name is managed by the broker
     *
     * @return
     */
    protected Queue declareTemporaryQueue() {
        return amqpAdmin.declareQueue();
    }

    /**
     * Create a queue with the specified name
     *
     * @return
     */
    protected Queue declareQueue(String name) {
        Queue queue = getQueue(name);
        declareQueue(queue);
        return queue;
    }

    protected Queue getQueue(String name) {
        Queue queue = new Queue(name);
        return queue;
    }

    protected void declareQueue(Queue queue) {

        amqpAdmin.declareQueue(queue);
    }

    protected void deleteQueue(String queueName) {
        amqpAdmin.deleteQueue(queueName);
    }

}
