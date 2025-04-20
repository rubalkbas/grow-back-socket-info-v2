package com.nezztech.apuesta.websocket.handler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Component
@Slf4j
public class MyWebSocketHandler2 implements WebSocketHandler {
	
	 private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	 
	 private final ObjectMapper objectMapper = new ObjectMapper();

	    public MyWebSocketHandler2() {
	    	log.info("MyWebSocketHandler2 instantiated");
	    }

	    @Override
	    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	        sessions.add(session);
	        log.info("Connection established with session ID: " + session.getId());
	        log.info("Current sessions: " + sessions.size());
	    }

	    @Override
	    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	        // No se reciben mensajes, solo se envían
	    }

	    @Override
	    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
	        // Manejar errores
	    	log.info("Error occurred: " + exception.getMessage());
	        removeSession(session);
	    }

	    @Override
	    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
	        removeSession(session);
	        log.info("Connection closed with session ID: " + session.getId());
	    }

	    private void removeSession(WebSocketSession session) {
	        sessions.remove(session);
	    }

	    @Override
	    public boolean supportsPartialMessages() {
	        return false;
	    }
	    
	    public void sendMessageToAllClients(Object data) {
	        String jsonMessage = convertToJson(data);
	        //System.out.println("Sending message to all clients: " + jsonMessage);
	        for (WebSocketSession session : sessions) {
	            if (session.isOpen()) {
	                try {
	                    session.sendMessage(new TextMessage(jsonMessage));
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    private String convertToJson(Object data) {
	        try {
	            return objectMapper.writeValueAsString(data);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	            return "{}"; // Devuelve un JSON vacío en caso de error
	        }
	    }

}
