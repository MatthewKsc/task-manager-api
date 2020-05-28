package com.matthewksc.taskmanager.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matthewksc.taskmanager.dao.entity.MyUserDetails;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class UserFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager manager;
    private JwtConfig jwt;

    public UserFilter(AuthenticationManager manager, JwtConfig jwt) {
        this.jwt = jwt;
        this.manager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UserRequest userRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UserRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userRequest.getUsername(),
                    userRequest.getPassword()
            );

            return manager.authenticate(authentication);
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(jwt.getKey().getBytes()))
                .compact();

        MyUserDetails myUserDetails = (MyUserDetails) authResult.getPrincipal();
        Long currentUserId = myUserDetails.getId(); //getting userId for request to UserController

        response.addHeader("UserId", Long.toString(currentUserId));
        response.addHeader("Access-Control-Expose-Headers", "Authorization, UserId");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        //response with this header to angular
        response.addHeader(jwt.getHeader(), jwt.getPrefix()+token);
    }
}
