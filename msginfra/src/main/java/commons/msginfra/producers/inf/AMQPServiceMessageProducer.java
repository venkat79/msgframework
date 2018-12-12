package commons.msginfra.producers.inf;

import commons.msginfra.entities.RPCServiceMessage;
import commons.msginfra.entities.ServiceMessage;

/**
 * An extension of 'ServiceMessageProducer to provide
 * AMQP specific Producer operations . Provides options to send
 * messages to specific exchanges and routing keys to override defaults.
 * Also provides send and wait for response if so desired in a blocking
 * RPC type message
 *
 *
 */
public interface AMQPServiceMessageProducer<REQUEST extends ServiceMessage,RESPONSE extends ServiceMessage> extends
        ServiceMessageProducer<REQUEST , RESPONSE > {

    /**
     * Send a message to specified exchange and routing key.
     * @param request
     * @param exchange
     * @param routingKey
     */
    void sendMessage(REQUEST request, String exchange, String routingKey);


    /**
     * Send a message to default exchange or configured exchange in the implementation
     * @param request
     * @param routingKey
     */
    void sendMessage(REQUEST request, String routingKey);

    /**
     * Send and wait for message from the receiver from a specific exchange and key.
     * @param request
     * @param exchange
     * @param routingKey
     * @return
     */
    RESPONSE exchangeMessage (REQUEST request, String exchange, String routingKey);
}

