package consumer.x2.events.generators;

import commons.msgentities.request.RequestBetaAsync;
import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.events.ServiceMessageEventGenerator;
import consumer.x2.events.entities.MyRequestBetaEvent;

public class MyRequestBetaEventGenerator implements ServiceMessageEventGenerator<MyRequestBetaEvent, RequestBetaAsync> {

    @Override
    public MyRequestBetaEvent generateEvent(RequestBetaAsync message, AMQPMessageBundle amqpMessageBundle) {
        return new MyRequestBetaEvent(this, message, amqpMessageBundle);
    }
}

