package com.chat.app.controller;

import com.chat.app.Repository.MessageRepository;
import com.chat.app.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<List<Message>> getPrivateMessage(@RequestParam String user1,@RequestParam String user2){
        List<Message> messages = messageRepository.findPrivateMessageBetweenTwoUser(user1,user2);
        return ResponseEntity.ok(messages);
    }
}
