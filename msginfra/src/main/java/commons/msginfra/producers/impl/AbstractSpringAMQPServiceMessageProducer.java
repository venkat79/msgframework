package commons.msginfra.producers.impl;

import commons.msginfra.entities.RPCServiceMessage;
import commons.msginfra.entities.ServiceMessage;
import commons.msginfra.producers.inf.AMQPServiceMessageProducer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A Spring AMQP implementation of 'AMQPServiceMessageProducer'. Assumes
 * a converter of type 'MessageConverter' is configured in 'AMQPTemplate'
 * Also provides implementations  to perform certain admin tasks e.g create queues,exchanges,bindings etc.
 * The current implementation is limited to default settings for Queues and Exchanges and
 * only supports DirectExchange but functionality can be added as needed.
 *
 *
 * @param <REQUEST>
 * @param <RESPONSE>
 */
public abstract class AbstractSpringAMQPServiceMessageProducer<REQUEST extends ServiceMessage,RESPONSE extends ServiceMessage>
        extends AbstractSpringAMQPAdmin implements AMQPServiceMessageProducer<REQUEST, RESPONSE> {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void sendMessage(REQUEST request) {
        amqpTemplate.convertAndSend(request);
    }

    @Override
    public RESPONSE exchangeMessage(REQUEST request) {
        return (RESPONSE) amqpTemplate.convertSendAndReceive(request);
    }

    @Override
    public void sendMessage(REQUEST request, String exchange, String routingKey) {
        amqpTemplate.convertAndSend(exchange, routingKey, request);
    }


    @Override
    public void sendMessage(REQUEST request, String routingKey) {
        amqpTemplate.convertAndSend(routingKey, request);
    }

    @Override
    public RESPONSE exchangeMessage(REQUEST request, String exchange, String routingKey) {
        return (RESPONSE) amqpTemplate.convertSendAndReceive(exchange,routingKey,request);

    }



}

