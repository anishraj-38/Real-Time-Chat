package com.chat.app.Repository;

import com.chat.app.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isOnline = :isOnline WHERE u.username = :username")
    public void updateUserOnlineStatus(@Param("username") String username,@Param("isOnline")boolean isOnline);
    User findByUsername(String username);
}

