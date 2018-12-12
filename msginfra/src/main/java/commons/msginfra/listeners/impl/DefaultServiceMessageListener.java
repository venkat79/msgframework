package commons.msginfra.listeners.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.RPCServiceMessage;
import commons.msginfra.entities.ServiceMessage;
import commons.msginfra.listeners.inf.ServiceMessageListener;
import commons.msginfra.processors.inf.ServiceMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

/**
 * Default implementation of ServiceMessageListener
 * The implementation forwards to 'ServiceMessageProcessor'
 * which processes the message in a separate thread. This listener, depending
 * on if a response is required synchronously for this message, waits
 * on the  Future of the thread otherwise returns an empty result back up the
 * messaging infrastructure.
 *
 */
public class DefaultServiceMessageListener implements ServiceMessageListener<ServiceMessage> {

    private static final Logger _logger = LoggerFactory.getLogger(DefaultServiceMessageListener.class);

    @Autowired
    private ServiceMessageProcessor<ServiceMessage> serviceMessageProcessor;


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        _logger.debug("Message body : {}", message.getBody());

        String bodyAsStr = new String(message.getBody(), "UTF-8");
        _logger.debug("Message body as string : {}", bodyAsStr);
        _logger.debug("Message properties : {}", message.getMessageProperties().toString());

        ObjectMapper mapper = new ObjectMapper();
        Class clazz = null;

        try {
            clazz = Class.forName((String) message.getMessageProperties().getHeaders().get("__TypeId__"));
            ServiceMessage serviceMessage = (ServiceMessage) mapper.readValue(bodyAsStr, clazz);
            handleMessage(serviceMessage, new AMQPMessageBundle(message, channel));
        } catch (ClassNotFoundException e) {
            // Ignore exception
        }



    }

    @Override
    public ServiceMessage handleMessage(ServiceMessage message, AMQPMessageBundle amqpMessageBundle) {
        if (message != null) {
            _logger.debug("Received service message of type: {}", message.getClass());
        }

        Future<ServiceMessage> response = serviceMessageProcessor.processMessage(message, amqpMessageBundle);
        if(response == null){
            // fill what the message is
            _logger.warn("Could not process message");
        }


        return null;
    }
    public void setServiceMessageProcessor(
            ServiceMessageProcessor<ServiceMessage> serviceMessageProcessor) {
        this.serviceMessageProcessor = serviceMessageProcessor;
    }




}
