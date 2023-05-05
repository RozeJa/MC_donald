package cz.rozek.jan.admin_mc_donald;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootApplication
public class AdminMcDonaldApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminMcDonaldApplication.class, args);

		WebSocketClient client = new StandardWebSocketClient();

		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		StompSessionHandler sessionHandler = new MyStompSessionHandler();
		stompClient.connectAsync("ws://localhost:8080/order/subscription", sessionHandler);

		new Scanner(System.in).nextLine(); // Don't close immediately.
	}

}
