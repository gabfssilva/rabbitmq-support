rabbitmq-support-core
================

RabbitMQ Client based on annotations for JAVA.

Basic usage:

Creating a consumer:

```
import com.wehavescience.rabbitmqsupport.consumer.RabbitMQQueueListener;
import com.wehavescience.rabbitmqsupport.consumer.annotations.RabbitMQConsumer;
import com.wehavescience.rabbitmqsupport.converters.DefaultConverter;


/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@RabbitMQConsumer(queue = "sample.queue", converterClass = StringConverter.class)
public class SampleConsumer implements RabbitMQQueueListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }
}
```

Registering it:

```
RabbitMQConfiguration config = new RabbitMQConfiguration(username(), password(), virtualhost(), urls());

RabbitMQSupport support = new RabbitMQSupport();

support.register(new SampleConsumer(), new AnotherConsumer());
```
