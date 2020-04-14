package com.thoughtworks.flywayDbMigration.service;

import com.thoughtworks.flywayDbMigration.dto.UserDTO;
import com.thoughtworks.flywayDbMigration.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${users.rabbitmq.exchange}")
    private String exchange;

    @Value("${users.rabbitmq.routingkey}")
    private String routingKey;

    public void send(UserDTO userDTO){
        rabbitTemplate.convertAndSend(exchange, "routingKey", userDTO);
        System.out.println("Send msg = " + userDTO );
    }



}
