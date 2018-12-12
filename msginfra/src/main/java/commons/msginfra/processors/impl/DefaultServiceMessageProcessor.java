package commons.msginfra.processors.impl;

import commons.msginfra.entities.AMQPMessageBundle;
import commons.msginfra.entities.AsyncServiceMessage;
import commons.msginfra.entities.RPCServiceMessage;
import commons.msginfra.entities.ServiceMessage;
import com.rabbitmq.client.Channel;
import commons.msginfra.events.ServiceMessageEventGenerator;
import commons.msginfra.processors.inf.ServiceMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Default 'ServiceMessageProcessor' implementation. This class on startup of a spring context
 * searches all beans of type 'ServiceMessageEventGenerator' and 'ServiceMessageProcessor' registered
 * in the current context and maps them vs the NodeMessage type they support.
 * When a message is received, it first checks if the message has an event generator registered and if so creates
 * the event using the mapped generator and publishes event.
 * If a processor is registered, it means its a synchronous direct call to process the message to an explicit processor
 * which is invoked and the response is sent back.
 *
 * Applications using  messaging need to provide an implementation of either 'ServiceMessageProcessor' or 'ServiceMessageEventGenerator'
 * for each ServiceMessage implementation and register them as spring beans in the application context.
 *
 */
public class DefaultServiceMessageProcessor implements ServiceMessageProcessor<ServiceMessage>, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger _logger = LoggerFactory.getLogger(DefaultServiceMessageProcessor.class);


    private ApplicationContext applicationContext;
    private Map<String, ServiceMessageEventGenerator> eventGeneratorMap ;
    private Map<String, ServiceMessageProcessor> messageProcessorMap ;


    @Override
    @Async("serviceMessageTaskExecutor")
    public Future<ServiceMessage> processMessage(ServiceMessage message, AMQPMessageBundle amqpMessageBundle) {

        _logger.debug("Service Msg Processor - default called");

        if(eventGeneratorMap == null || messageProcessorMap == null) {
            _logger.warn("Event processors/generators are not initialized. Initializing the event processors & generators...");
            eventGeneratorMap = initializeEventGenerators(ServiceMessageEventGenerator.class);
            messageProcessorMap = initializeProcessors(ServiceMessageProcessor.class);
        }

        if(eventGeneratorMap!=null) {
            ServiceMessageEventGenerator<ApplicationEvent, ServiceMessage> eventGenerator =
                    eventGeneratorMap.get(message.getClass().getName());
            if (eventGenerator != null) {
                applicationContext.publishEvent(eventGenerator.generateEvent(message, amqpMessageBundle));
                return new AsyncResult<>(null);
            }
        }else{
            _logger.debug("Message will not be processed since EventGeneratorMap is null.");
        }

        ServiceMessageEventGenerator<ApplicationEvent, ServiceMessage> eventGenerator
                = eventGeneratorMap.get(message.getClass().getName());
        if (eventGenerator != null) {
            applicationContext.publishEvent(eventGenerator.generateEvent(message, amqpMessageBundle));
            return new AsyncResult<>(null);
        } else {
            _logger.trace("Missing event generator for the type : {}", message.getClass().getName());
        }

        ServiceMessageProcessor<ServiceMessage> messageProcessor = messageProcessorMap.get(message.getClass().getName());
        if (messageProcessor != null) {
            return messageProcessor.processMessage(message, amqpMessageBundle);
        } else {
            _logger.debug("Missing message processor for the type : {}", message.getClass().getName());
            Channel channel = amqpMessageBundle.getChannel();
            Message deadMessage = amqpMessageBundle.getMessage();
            try {
                // Send to dead queue
                channel.basicPublish("ecra.dead.exchange","ecra.dead.routingKey",null,deadMessage.getBody());
                // reject the message from current queue
                channel.basicAck(amqpMessageBundle.getMessage().getMessageProperties().getDeliveryTag(), true);


            } catch (IOException e) {
                _logger.debug("Exception while publish to dead exchange", e);
            }


        }

        _logger.debug("Before sending the response");
        return new AsyncResult<>(null);
    }


    private <V> Map<String,V> initializeProcessors(Class<V> clazz){
        Map<String,V> responseMap = new HashMap<>();
        Collection<V> messageProcessors= applicationContext.getBeansOfType(clazz).values();
        _logger.debug("Found Processors  {} ",messageProcessors);
        for(V eachProcessor : messageProcessors){
            _logger.debug("Checking Processor {} ",eachProcessor);

            Type[] types = eachProcessor.getClass().getGenericInterfaces();
            for( Type eachInterfaceType : types){

                if(eachInterfaceType instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) eachInterfaceType;
                    _logger.debug("Parameterized Type of processor interface  {} ",parameterizedType);
                    if (! TypeUtils.isAssignable(parameterizedType, clazz)){
                        continue;
                    }
                    Type[] typeArgs = ((ParameterizedType)eachInterfaceType).getActualTypeArguments();
                    for(Type eachArg :typeArgs){

                        if(TypeUtils.isAssignable(RPCServiceMessage.class, eachArg) && !eachProcessor.equals(this)){
                            _logger.debug("Adding processor to map {} for class  {} ",eachProcessor,eachArg.toString());
                            responseMap.put(((Class)eachArg).getName(), eachProcessor);
                        }
                    }
                }
            }
        }

        return responseMap;

    }

    private <V> Map<String,V> initializeEventGenerators(Class<V> clazz){
        Map<String,V> responseMap = new HashMap<>();
        Collection<V> eventGenerators = applicationContext.getBeansOfType(clazz).values();
        _logger.debug("Found Generators  {} ", eventGenerators);
        for(V eachProcessor : eventGenerators){
            _logger.debug("Checking Generator {} ",eachProcessor);

            Type[] types = eachProcessor.getClass().getGenericInterfaces();
            for( Type eachInterfaceType : types){

                if(eachInterfaceType instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) eachInterfaceType;
                    _logger.debug("Parameterized Type of generator interface  {} ",parameterizedType);
                    if (! TypeUtils.isAssignable(parameterizedType, clazz)){
                        continue;
                    }
                    Type[] typeArgs = ((ParameterizedType)eachInterfaceType).getActualTypeArguments();
                    for(Type eachArg :typeArgs){

                        if(TypeUtils.isAssignable(AsyncServiceMessage.class, eachArg) && !eachProcessor.equals(this)){
                            _logger.debug("Adding generator to map {} for class  {} ",eachProcessor,eachArg.toString());
                            responseMap.put(((Class)eachArg).getName(), eachProcessor);
                        }
                    }
                }
            }
        }

        return responseMap;

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;

    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!event.getApplicationContext().getId().equals(applicationContext.getId())){
            return;
        }
        eventGeneratorMap = initializeEventGenerators(ServiceMessageEventGenerator.class);
        messageProcessorMap = initializeProcessors(ServiceMessageProcessor.class);

        _logger.debug("eventGeneratorMap : {}", eventGeneratorMap.toString());
        _logger.debug("messageProcessorMap : {}", messageProcessorMap.toString());
    }


}