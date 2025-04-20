package com.nezztech.apuesta.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.nezztech.apuesta.websocket.handler.MyWebSocketHandler;
import com.nezztech.apuesta.websocket.handler.MyWebSocketHandler2;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
    private MyWebSocketHandler myWebSocketHandler;
	
	@Autowired
    private MyWebSocketHandler2 myWebSocketHandler2;
	
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler, "/websocket").setAllowedOrigins("*");
        registry.addHandler(myWebSocketHandler2, "/websocket-ganper").setAllowedOrigins("*");
    }	

}
