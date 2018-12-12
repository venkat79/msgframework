package consumer.x2.events.generators;


import commons.msgentities.request.RequestAlphaAsync;
import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.events.ServiceMessageEventGenerator;
import consumer.x2.events.entities.MyRequestAlphaEvent;

public class MyRequestAlphaEventGenerator implements ServiceMessageEventGenerator<MyRequestAlphaEvent, RequestAlphaAsync> {

    @Override
    public MyRequestAlphaEvent generateEvent(RequestAlphaAsync message, AMQPMessageBundle amqpMessageBundle) {
        return new MyRequestAlphaEvent(this, message, amqpMessageBundle);
    }
}
