package cz.rozek.jan.mc_donald.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import cz.rozek.jan.mc_donald.models.Order;

@RestController
public class WebSocketController {
    
    @MessageMapping("/distributeOrder") // /app/distributeOrder přes websocket protokol pošle request na tuto metodu
    @SendTo("/orders/distributeOrder") // to co dosta ne přepošle na ty, kdo odebýrají websocket na tété adrese
    public Order distributeOrder(Order order) {
        return order;
    }
}
