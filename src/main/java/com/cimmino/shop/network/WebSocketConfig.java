package com.cimmino.shop.network;

//This is the configuration class for WebSocket

//connections. It enables WebSocket and registers the
//SocketConnectionHandler class as the handler for the
//"/hello" endpoint. It also sets the allowed origins to
//"*" so that other domains can also access the socket.

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final SocketConnectionHandler handler;

	public WebSocketConfig(SocketConnectionHandler handler) {

		this.handler = handler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		System.out.println("WebSocket enabled");

		registry.addHandler(handler, "/hello").setAllowedOrigins("*");
	}
}