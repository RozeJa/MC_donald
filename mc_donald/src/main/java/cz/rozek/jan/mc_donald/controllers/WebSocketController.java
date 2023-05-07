package cz.rozek.jan.mc_donald.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import cz.rozek.jan.mc_donald.models.Order;

@RestController
public class WebSocketController {
    
    @MessageMapping("/distributeOrder")
    @SendTo("/orders/distributeOrder")
    public Order distributeOrder(Order order) {
        return order;
    }
}
