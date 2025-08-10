package com.chat.app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="Chat_Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String recepient;
    private String content;
    private  String color;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private MessageType type;
    public enum MessageType{
        CHAT,PRIVATE_MESSAGE,JOIN,LEAVE, MessageType, TYPING
    }
}

