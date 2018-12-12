package consumer.x2.events.entities;

import commons.msgentities.request.RequestAlphaAsync;
import commons.msgentities.request.RequestBetaAsync;
import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.events.AbstractServiceMessageEvent;

public class MyRequestBetaEvent extends AbstractServiceMessageEvent<RequestBetaAsync> {

    public MyRequestBetaEvent(Object source, RequestBetaAsync requestBetaAsync, AMQPMessageBundle amqpMessageBundle){
        super(source, requestBetaAsync, amqpMessageBundle);
    }
}
