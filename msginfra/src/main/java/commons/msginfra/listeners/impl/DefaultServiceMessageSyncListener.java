package commons.msginfra.listeners.impl;

import commons.msginfra.entities.RPCServiceMessage;
import commons.msginfra.entities.ServiceMessage;
import commons.msginfra.listeners.inf.ServiceMessageSyncListener;
import commons.msginfra.processors.inf.ServiceMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

public class DefaultServiceMessageSyncListener implements ServiceMessageSyncListener<ServiceMessage> {

    private static final Logger _logger = LoggerFactory.getLogger(DefaultServiceMessageSyncListener.class);

    @Autowired
    private ServiceMessageProcessor<ServiceMessage> serviceMessageProcessor;


    @Override
    public ServiceMessage handleMessage(ServiceMessage message) {
        if (message != null) {
            _logger.debug("Received service message of type: {}", message.getClass());
        }


        Future<ServiceMessage> response = serviceMessageProcessor.processMessage(message, null);
        if (response == null) {
            // fill what the message is
            _logger.warn("Could not process message");
        }


        if (RPCServiceMessage.class.isAssignableFrom(message.getClass())) {
            try {
                _logger.debug("It is an RPC message");
                return response.get();
            } catch (Exception e) {
                // Fill what the message is
                _logger.error("Error handling message", e);

            }
        }
        return null;
    }

    public void setServiceMessageProcessor(
            ServiceMessageProcessor<ServiceMessage> serviceMessageProcessor) {
        this.serviceMessageProcessor = serviceMessageProcessor;
    }
}





