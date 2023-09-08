package com.ead.authuser.publisher;

import com.ead.authuser.DTOs.UserEventDTO;
import com.ead.authuser.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDTO userEventDTO, ActionType actionType) {
        userEventDTO.setActionType(actionType.toString());

        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDTO);
    }
}
