package org.example.adreessmanagement.listener;


import org.example.adreessmanagement.model.Address;
import org.example.adreessmanagement.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AddressMessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Address address) {
        System.out.println("📥 Received Message: " + address);
    }
}

