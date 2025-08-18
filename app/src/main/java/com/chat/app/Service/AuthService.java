package com.chat.app.Service;

import com.chat.app.Repository.UserRepository;
import com.chat.app.dto.LoginRequestDTO;
import com.chat.app.dto.LoginResponseDTO;
import com.chat.app.dto.RegisterRequestDTO;
import com.chat.app.dto.UserDTO;
import com.chat.app.jwt.JwtService;
import com.chat.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO signup(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("UserName is already used ");

        }
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode((registerRequestDTO.getPassword())));
        user.setEmail(registerRequestDTO.getEmail());

        User savedUser = userRepository.save(user);
        return convertToUserDTO(user);

    }



    public LoginResponseDTO loginResponseDTO(LoginRequestDTO loginRequestDTO){
      Optional<User> user = userRepository.findByUsername((loginRequestDTO.getUsername().describeConstable().orElseThrow(()-> new RuntimeException("Username not found"))));
              authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword()));
      String jwtToken = jwtService.generateToken(user);
      return LoginResponseDTO.builder()
              .token(jwtToken)
              .userDTO(convertToUserDTO(user.orElse(null)))
              .build();

    }


    public UserDTO convertToUserDTO(User user){UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }


}
