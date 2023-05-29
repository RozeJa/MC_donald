package cz.rozek.jan.mc_donald.stompHandlers;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import cz.rozek.jan.mc_donald.models.Order;

public class OrderStompSessionHandler implements StompSessionHandler {
    
    private StompSession session;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) { 
        session.subscribe("/orders/distributeOrder", this);
        this.session = session;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Order.class;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println(exception);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
       //System.out.println(((Order) payload).getId());        
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println(exception);
    }

    public void sendOrder(Order order) {
        session.send("/app/distributeOrder", order);
    }
}
