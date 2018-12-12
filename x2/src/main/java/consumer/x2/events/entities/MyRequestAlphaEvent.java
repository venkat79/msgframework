package consumer.x2.events.entities;


import commons.msgentities.request.RequestAlphaAsync;
import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.events.AbstractServiceMessageEvent;

public class MyRequestAlphaEvent extends AbstractServiceMessageEvent<RequestAlphaAsync> {

    public MyRequestAlphaEvent(Object source, RequestAlphaAsync myRequestAlpha, AMQPMessageBundle amqpMessageBundle){
        super(source, myRequestAlpha, amqpMessageBundle);
    }
}
