package com.chat.app.Repository;


import com.chat.app.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.type = 'PRIVATE_MESSAGE' AND " +
            "((m.sender = :user1 AND m.recepient = :user2) OR " +
            "(m.sender = :user2 AND m.recepient = :user1))")
    List<Message> findPrivateMessageBetweenTwoUser(@Param("user1")String user1,@Param("user2")String user2);
}
