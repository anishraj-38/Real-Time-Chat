-> Real-Time Chat Application

A real-time chat web application built using Spring Boot, WebSocket, and STOMP protocol.
Supports private messaging, group chat, user online/offline status, and JWT-based authentication.

This project is a modern chat application where multiple users can communicate instantly without page reloads.
It uses WebSocket for real-time messaging and Spring Security + JWT for secure authentication.

-> Features

User Authentication (JWT-based login & registration)
Real-Time Messaging (powered by WebSocket & STOMP)
Public Chat Room
Private Messaging
User Online/Offline Status tracking

-> Tech Stack

Backend: Spring Boot, Spring WebSocket, Spring Security, JWT
Frontend: (  React)
Database: H2 SQL
Messaging Protocol: STOMP over WebSocket

-> Here is project structure
chat-app/
│── src/main/java/com/chat/app
│   ├── config/         # WebSocket & Security Configurations
│   ├── controller/     # Chat & Auth Controllers
│   ├── dto/            # Data Transfer Objects
│   ├── models/         # Entity Classes
│   ├── repository/     # JPA Repositories
│   ├── service/        # Business Logic
│── src/main/resources/
│   ├── application.properties  # App Config
│── pom.xml
│── README.md
