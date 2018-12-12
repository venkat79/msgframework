package producer.x1.producerclient.impl;

import commons.msgentities.request.RequestAlphaAsync;
import commons.msgentities.request.RequestAlphaSync;
import commons.msgentities.request.RequestBetaAsync;
import commons.msgentities.request.RequestBetaAsyncMiss;
import commons.msgentities.response.ResponseX2;
import commons.msginfra.entities.ServiceMessage;
import commons.msginfra.producers.impl.AbstractSpringAMQPServiceMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import producer.x1.producerclient.inf.MyX1Producer;

@Service
public class MyX1ProducerImpl extends AbstractSpringAMQPServiceMessageProducer<ServiceMessage, ServiceMessage>
        implements MyX1Producer {

    private static final Logger _logger = LoggerFactory.getLogger(MyX1ProducerImpl.class);


    @Override
    public void sentMyRequestAlphaASync(RequestAlphaAsync requestAlphaAsync) {
        sendMessage(requestAlphaAsync, "ecra.service.exchange", "ecra.service.routingKey");
    }

    @Override
    public void sentMyRequestBetaAsyncMiss(RequestBetaAsyncMiss requestBetaAsyncMiss) {
        sendMessage(requestBetaAsyncMiss, "ecra.service.exchange", "ecra.service.routingKey");
    }

    @Override
    public void sentMyRequestBetaAsync(RequestBetaAsync requestBetaAsync) {
        sendMessage(requestBetaAsync, "ecra.service.exchange", "ecra.service.routingKey");
    }

    @Override
    public ResponseX2 sendMyRequestAlphaSync(RequestAlphaSync requestAlphaSync) {
        ResponseX2 responseX2 = (ResponseX2) exchangeMessage(requestAlphaSync,
                "ecra.service.exchange", "ecra.service.sync.routingKey");
        _logger.debug("Response received : {}", responseX2);
        return responseX2;
    }
}
