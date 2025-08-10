package com.chat.app.Listner;

import com.chat.app.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.logging.Logger;

import static com.chat.app.Service.UserService.*;

@Component
public class WebSocketListner {
    @Autowired
    private UserService userService;

    @Autowired
    private static final Logger logger = (Logger) LoggerFactory.getLogger(WebSocketListner.class);

    @EventListener
    public void handleWebsocketConnectListener(SessionConnectedEvent event){
        logger.info(" Connected to websocket ");
    }
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getSessionAttributes().get("username").toString();

       userService.setUserOnlineStatus(username,false);
    }
}
