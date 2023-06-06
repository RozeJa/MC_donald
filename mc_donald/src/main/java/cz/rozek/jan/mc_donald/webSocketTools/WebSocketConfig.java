package cz.rozek.jan.mc_donald.webSocketTools;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/orders"); // povolený začátek na kterém můžou být endpointy pro odběr
    config.setApplicationDestinationPrefixes("/app"); // dotazy směrem na server s využitím websocketu na /app př /app/distributeOrder
  
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    //zaregistruje websocket pro adresu ws://adresa:port/ws 
    // jednou bez a jednou s SockJS
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
  }

}
