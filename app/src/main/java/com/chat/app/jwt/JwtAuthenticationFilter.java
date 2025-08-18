package com.chat.app.jwt;

import com.chat.app.Repository.UserRepository;
import com.chat.app.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         Long userId;
         String jwtToken=null;

         String authHeader = request.getHeader("Authorization");

         if(authHeader != null && authHeader.startsWith("Bearer")){
             jwtToken=authHeader.substring(7);
         }

         if(jwtToken==null){
             Cookie [] cookies = request.getCookies();
             if(cookies!=null){
                 for(Cookie cookie : cookies){
                     if("JWT".equals(cookie.getName())){
                         jwtToken=cookie.getValue();
                         break;
                     }
                 }
             }
         }
         if(jwtToken==null){
             filterChain.doFilter(request,response);
         }
         userId=jwtService.extractUserId(jwtToken);

         if(userId !=null && SecurityContextHolder.getContext().getAuthentication() == null){
             var userDetails = userRepository.findById(userId)
                     .orElseThrow(() -> new RuntimeException("User not found "));

             if(jwtService.isTokenValid(jwtToken,userDetails)){
                 UsernamePasswordAuthenticationToken  authToken = new UsernamePasswordAuthenticationToken(userDetails,null, Collections.emptyList());
                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authToken);

             }

         }

         filterChain.doFilter(request,response);

    }
}
