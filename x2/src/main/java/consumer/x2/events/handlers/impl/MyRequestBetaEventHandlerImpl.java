package consumer.x2.events.handlers.impl;

import commons.msgentities.request.RequestAlphaAsync;
import commons.msgentities.request.RequestBetaAsync;
import commons.msginfra.entities.AMQPMessageBundle;
import consumer.x2.events.entities.MyRequestAlphaEvent;
import consumer.x2.events.entities.MyRequestBetaEvent;
import consumer.x2.events.handlers.inf.MyRequestAlphaEventHandler;
import consumer.x2.events.handlers.inf.MyRequestBetaEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import com.rabbitmq.client.Channel;


import java.io.IOException;

public class MyRequestBetaEventHandlerImpl implements ApplicationListener<MyRequestBetaEvent>, MyRequestBetaEventHandler {

    private static final Logger _logger = LoggerFactory.getLogger(MyRequestAlphaEventHandlerImpl.class);

    @Override
    public void onApplicationEvent(MyRequestBetaEvent myRequestBetaEvent) {
        RequestBetaAsync requestBetaAsync= myRequestBetaEvent.getMessage();
        processReq(requestBetaAsync);
        AMQPMessageBundle amqpMessageBundle = myRequestBetaEvent.getAMQPMessageBundle();

        Channel channel = amqpMessageBundle.getChannel();

        try {
            if (false) Thread.sleep(60000);

            _logger.debug("This is requestBetaEvent");
            channel.basicAck(myRequestBetaEvent.getAMQPMessageBundle().getMessage().getMessageProperties().getDeliveryTag(), false);
            _logger.debug("Acked.... I am done");
        } catch (IOException e) {
            _logger.error("Exception occurred while sending Ack", e);
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            // Ignore
        }



    }

    @Override
    public void processReq(RequestBetaAsync requestBetaAsync) {
        _logger.debug("Request received through handler : {}", requestBetaAsync.toString());
        _logger.debug("Cool! --- My request is : {}", requestBetaAsync.toString());

    }
}