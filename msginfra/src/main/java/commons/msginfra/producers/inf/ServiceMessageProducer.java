package commons.msginfra.producers.inf;

import commons.msginfra.entities.RPCServiceMessage;
import commons.msginfra.entities.ServiceMessage;

/**
 * Interface for send & exchange message
 * @param <REQUEST>
 * @param <RESPONSE>
 */
public interface ServiceMessageProducer<REQUEST extends ServiceMessage,RESPONSE extends ServiceMessage> {

    /**
     * Sends a message to destination
     * in non blocking mode and returns immediately
     * @param request
     */
    void sendMessage(REQUEST request);


    /**
     * Sends a message and waits for a response from the destination
     * receiver.
     * @param request
     * @return
     */
    RESPONSE exchangeMessage (REQUEST request);
}
