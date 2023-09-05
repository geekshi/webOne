package web;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_INFORM_STOCK_UPDATE = "queue_inform_stock_update";

    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    public static final String ROUTINGKEY_STOCK_UPDATE = "inform.#.stockupdate.#";

    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    @Bean(QUEUE_INFORM_STOCK_UPDATE)
    public Queue QUEUE_INFORM_STOCK_UPDATE() {
        return new Queue(QUEUE_INFORM_STOCK_UPDATE);
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_STOCK_UPDATE(@Qualifier(QUEUE_INFORM_STOCK_UPDATE) Queue queue,
                                                     @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_STOCK_UPDATE).noargs();
    }
}
