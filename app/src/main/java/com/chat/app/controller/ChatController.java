package com.chat.app.controller;


import com.chat.app.Repository.MessageRepository;
import com.chat.app.Service.UserService;
import com.chat.app.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        if (userService.userExists(message.getSender())) {
            headerAccessor.getSessionAttributes().put("username", message.getSender());
            userService.setUserOnlineStatus(message.getSender(), true);
            System.out.println("User added Successfully " + message.getSender() + " with session ID"
                    + headerAccessor.getSessionId());
            message.setTimestamp(LocalDateTime.now());
            if (message.getContent() == null) {
                message.setContent("");
            }
            return messageRepository.save(message);
        }
        return null;

    }

    @MessageMapping("chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        if (userService.userExists(message.getSender())) {
            if (message.getTimestamp() == null) {
                message.setTimestamp(LocalDateTime.now());
            }
            if (message.getContent() == null) {
                message.setContent("");

            }
            return messageRepository.save(message);
        }
        return null;
    }

    @MessageMapping("chat.sendPrivateMessage")
    @SendToUser("/topic/public")
    public void sendPrivateMessage(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        if (userService.userExists(message.getSender()) && userService.userExists(message.getRecepient())) {
            if(message.getTimestamp()==null){
                message.setTimestamp(LocalDateTime.now());
            }

            if(message.getContent()==null){
                message.setContent("");
            }
                message.setType(Message.MessageType.MessageType.PRIVATE_MESSAGE);

                Message savedMessage = messageRepository.save(message);
                System.out.println("Message saved successfully with id " + savedMessage.getId());


            try {
                String recepientDestination = "/user/" + message.getRecepient() + "/queue/private";
                System.out.println("Sending message to recepient destination " + recepientDestination);

                String senderDestination = "/user/" + message.getSender() + "/queue/private";
                System.out.println("Sending message to sender destination " + senderDestination);
                simpMessagingTemplate.convertAndSend(senderDestination, savedMessage);
            }
            catch(Exception e){
                System.out.println("ERROR occured while sending message "+e.getMessage());
            }
        }
        else{
            System.out.println("Error : sender "+message.getSender()+" Recepient "+message.getRecepient()+" doesn't exit ");
        }
    }
}



