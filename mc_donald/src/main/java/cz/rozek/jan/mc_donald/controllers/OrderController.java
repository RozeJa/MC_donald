package cz.rozek.jan.mc_donald.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import cz.rozek.jan.mc_donald.models.Order;
import cz.rozek.jan.mc_donald.services.DuplicateKeyException;
import cz.rozek.jan.mc_donald.services.OrderService;
import cz.rozek.jan.mc_donald.services.ValidationException;
import cz.rozek.jan.mc_donald.stompHandlers.OrderStompSessionHandler;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private OrderStompSessionHandler orderHandler;
 
    public OrderController() {
		WebSocketClient client = new StandardWebSocketClient();

		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        orderHandler = new OrderStompSessionHandler();
        stompClient.connectAsync("ws://localhost:8080/ws", orderHandler);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAll() {
        try {

            List<Order> orders = orderService.readAll().stream().filter(Order::isAvailable).toList();

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable String id) {
        try {
            Order order = orderService.read(id);

            if (!order.isAvailable())
                throw new NullPointerException();

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        try {
            Order o = orderService.create(order);

            orderHandler.sendOrder(o);

            return new ResponseEntity<>(o, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable String id, @RequestBody Order order) {
        try {
            Order o = orderService.update(id, order);
            
            orderHandler.sendOrder(o);

            return new ResponseEntity<>(o, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> delete(@PathVariable String id) {
        try {
            Order o = orderService.delete(id);

            return new ResponseEntity<>(o, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data_recovery/")
    public ResponseEntity<List<Order>> getRemoved() {
        try {
            List<Order> orders = orderService.readAll().stream().filter(o -> !o.isAvailable()).toList();

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
