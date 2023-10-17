package com.giftandgain.userservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgain.userservice.constants.constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // getting user and pass from http request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("User name is: {}", username);
        log.info("Password is: {}", password);
        // need to use ObjectMapper if request is in body as json object(?)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // send access token, refresh token whenever login successful
        // getPrincipal is the user details, but in object form
        // need to typecast into our User model
        User user = (User) authentication.getPrincipal();
        // JWT algorithm to sign the access and refresh token
        Algorithm algorithm = Algorithm.HMAC256(constants.JWT_ALGO_SECRET.getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                // Access token usually last few min, so that they can use refresh token
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                // Issuer - can be company name / author
                .withIssuer(request.getRequestURL().toString())
                // roles
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        // so typically, BE send back the tokens
        // frontend need to store the token on client side
        // send request with access token
        // usually is wait till access token expire, receive the expired token error msg
        // from BE, then use refresh token send request
        // get another access token
        // seamless experience - user doesnt know token expired, requested new token
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);





    }

    // can override unsuccessfulAuthentication
    // if you wanna do something when user attempts too many times
}
