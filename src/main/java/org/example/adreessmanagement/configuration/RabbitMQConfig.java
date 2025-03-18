package org.example.adreessmanagement.configuration;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "addressQueue";
    public static final String EXCHANGE_NAME = "addressExchange";
    public static final String ROUTING_KEY = "addressRoutingKey";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // durable queue
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
