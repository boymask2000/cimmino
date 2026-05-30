package com.cimmino.shop.network;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketConnectionHandler
        extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(
            WebSocketSession session)
            throws Exception {

        System.out.println(
                "Client connected: "
                        + session.getId());

        session.sendMessage(
                new TextMessage("Connected"));
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message)
            throws Exception {

        String payload =
                message.getPayload();

        System.out.println(
                "Received: " + payload);

        session.sendMessage(
                new TextMessage(
                        "ACK: " + payload));
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status)
            throws Exception {

        System.out.println(
                "Disconnected: "
                        + session.getId());
    }

    @Override
    public void handleTransportError(
            WebSocketSession session,
            Throwable exception)
            throws Exception {

        System.out.println(
                "Transport error");

        exception.printStackTrace();
    }
}