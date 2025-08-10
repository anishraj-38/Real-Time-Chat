package com.chat.app.Service;

import com.chat.app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public void setUserOnlineStatus(String username,boolean isOnline){
         userRepository.updateUserOnlineStatus(username,isOnline);
    }
}
