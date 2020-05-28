package com.matthewksc.taskmanager.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TokenVerify extends OncePerRequestFilter {

    private JwtConfig jwt;

    public TokenVerify(JwtConfig jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwt.getHeader());
        if (authorizationHeader.isEmpty() || authorizationHeader.equals(null)
                || !authorizationHeader.startsWith(jwt.getPrefix())){
            filterChain.doFilter(request,response);
            return;
        }

        try {
            String token = authorizationHeader.replace(jwt.getPrefix(), "");

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwt.getKey().getBytes()))
                    .build()
                    .parseClaimsJws(token);

            String username = claimsJws
                    .getBody()
                    .getSubject();

            List<Map<String, String>>  authorities = (List<Map<String, String>>) claimsJws
                    .getBody()
                    .get("authorities");

            List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities
                    .stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException exception){
            throw new IllegalStateException("Wrong token");
        }

        filterChain.doFilter(request, response);
    }
}
