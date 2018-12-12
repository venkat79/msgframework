package consumer.x2.events.handlers.impl;

import commons.msgentities.request.RequestAlphaAsync;
import commons.msginfra.entities.AMQPMessageBundle;
import consumer.x2.events.entities.MyRequestAlphaEvent;
import consumer.x2.events.handlers.inf.MyRequestAlphaEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import com.rabbitmq.client.Channel;


import java.io.IOException;

public class MyRequestAlphaEventHandlerImpl implements ApplicationListener<MyRequestAlphaEvent>, MyRequestAlphaEventHandler {

    private static final Logger _logger = LoggerFactory.getLogger(MyRequestAlphaEventHandlerImpl.class);

    @Override
    public void onApplicationEvent(MyRequestAlphaEvent myRequestAlphaEvent) {
        RequestAlphaAsync myRequestAlpha = myRequestAlphaEvent.getMessage();
        processReq(myRequestAlpha);
        AMQPMessageBundle amqpMessageBundle = myRequestAlphaEvent.getAMQPMessageBundle();

        Channel channel = amqpMessageBundle.getChannel();

        try {
            if (false) Thread.sleep(60000);
            _logger.debug("This is request alpha event");
            channel.basicAck(myRequestAlphaEvent.getAMQPMessageBundle().getMessage().getMessageProperties().getDeliveryTag(), false);
            _logger.debug("Acked.... I am done");
        } catch (IOException e) {
            _logger.error("Exception occurred while sending Ack", e);
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            // Ignore
        }



    }

    @Override
    public void processReq(RequestAlphaAsync myRequestAlpha) {
        _logger.debug("Request received through handler : {}", myRequestAlpha.toString());
        _logger.debug("Cool! --- My request is : {}", myRequestAlpha.toString());

    }
}
