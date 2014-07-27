rabbitmq-support-cdi
================

RabbitMQ Client based on annotations for JAVA - CDI Module

If you want to use rabbitmq-support using CDI, you should be using this module.

Basic usage:

```
@Inject
@RabbitMQContext(username = "guest", password = "guest", virtualhost = "/", urls = "localhost:5672;localhost:5673")
private RabbitMQConsumerSupportCDI rabbitMQConsumerSupportCDI;

@Inject
@RabbitMQContext(username = "guest", password = "guest", virtualhost = "/", urls = "localhost:5672;localhost:5673")
private RabbitMQProducerSupportCDI rabbitMQProducerSupportCDI;


rabbitMQConsumerSupportCDI.register();
rabbitMQProducerSupportCDI.publish("message", "sample.queue");
```

rabbitmq-support-cdi will scan every class which contains the @RabbitMQConsumer, get the instance from Weld context and bind it as a consumer.

If you don't feel alright passing the informations using the @RabbitMQContext annotation, you can use it in other ways:

```
@Produces
public RabbitMQConsumerSupportCDI rabbitMQConsumerSupportCDI(@Consumers List<RabbitMQQueueListener> listeners) throws IOException {
    RabbitMQConfiguration configuration = new RabbitMQConfiguration("guest", "guest", "/", "localhost:5672;localhost:5673");
    return new RabbitMQConsumerSupportCDI(configuration, listeners);
}

@Produces
public RabbitMQProducerSupportCDI rabbitMQProducerSupportCDI() throws IOException {
    RabbitMQConfiguration configuration = new RabbitMQConfiguration("guest", "guest", "/", "localhost:5672;localhost:5673");
    return new RabbitMQProducerSupportCDI(configuration);
}
```

Or even just:

```
@Produces
public RabbitMQSupport rabbitMQConfiguration() throws IOException {
    return new RabbitMQSupport(new RabbitMQConfiguration("guest", "guest", "/", "localhost:5672;localhost:5673"));
}
```