package cz.rozek.jan.admin_mc_donald.data;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import cz.rozek.jan.admin_mc_donald.data.models.Order;
import cz.rozek.jan.admin_mc_donald.gui.MainFrame;


public class OrderStompSessionHandler implements StompSessionHandler {

    private MainFrame mainFrame;

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/orders/distributeOrder", this);
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
        Order order = (Order) payload;
        mainFrame.addOrderToPrint(order);
        System.out.println(order.toString());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println(exception);
    }


}