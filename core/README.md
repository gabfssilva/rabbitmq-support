rabbitmq-support-core
================

RabbitMQ Client based on annotations for JAVA.

Basic usage:

Creating a consumer:

```java
import com.wehavescience.rabbitmqsupport.consumer.annotations.OnMessage;
import com.wehavescience.rabbitmqsupport.consumer.annotations.RabbitMQConsumer;


/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@RabbitMQConsumer(queue = "sample.queue", converterClass = StringConverter.class)
public class SampleConsumer {

    @OnMessage
    public void onMessage(String s) {
        System.out.println(s);
    }
}
```

Registering it:

```java
RabbitMQConfiguration config = new RabbitMQConfiguration(username(), password(), virtualhost(), urls());

RabbitMQSupport support = new RabbitMQSupport(config);

support.register(new SampleConsumer(), new AnotherConsumer());
```
