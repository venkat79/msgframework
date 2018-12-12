package consumer.x2.events.processors;

import commons.msgentities.request.RequestAlphaSync;
import commons.msgentities.response.ResponseX2;
import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.ServiceMessage;
import commons.msginfra.processors.inf.ServiceMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;


import java.util.concurrent.Future;

public class MyRequestAlphaSyncProcessor implements ServiceMessageProcessor<RequestAlphaSync> {

    private static final Logger _logger = LoggerFactory.getLogger(MyRequestAlphaSyncProcessor.class);

    @Override
    public Future<ServiceMessage> processMessage(RequestAlphaSync message, AMQPMessageBundle amqpMessageBundle) {
        _logger.debug("VENKAT--> {}", message.toString());
        ResponseX2 responseX2 = new ResponseX2();

        String UUID = java.util.UUID.randomUUID().toString();
        _logger.debug("UUID Generated --> {}", UUID);

        responseX2.setUuid(UUID);
        return new AsyncResult(responseX2);
    }
}

