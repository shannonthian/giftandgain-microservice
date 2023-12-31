package com.giftandgain.userservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgain.userservice.constants.constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import com.giftandgain.userservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.giftandgain.userservice.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/account")
    public ResponseEntity <User> updateUser(@RequestBody UpdateUserForm form) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return  ResponseEntity.created(uri).body(userService.updateUser(form.getName(),
                form.getUsername(), form.getEmail(), form.getAuthorities()));
    }
    @GetMapping("/account")
    public ResponseEntity <User> getUser(@RequestHeader Map<String,String> reqHeader) { return ResponseEntity.ok().body(userService.getUser(reqHeader.get("authorization")));}

    @GetMapping("/users")
    public ResponseEntity <List<User>> getUsers() {
        return  ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity <User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return  ResponseEntity.created(uri).body(userService.saveUser(user));
    }


    @PostMapping("/role/addtouser")
    public ResponseEntity <?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getAuthorities());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                // TODOBEN - refactor algorithm?
                // need to ensure that the sign and verify both same algo
                // JWT Refresh Token - so typically, BE send back the tokens
                // frontend need to store the token on client side
                // send request with access token
                // usually is wait till access token expire, receive the expired token error msg
                // from BE, then use refresh token send request
                // get another access token
                // seamless experience - user doesn't know token expired, requested new token
                Algorithm algorithm = Algorithm.HMAC256(constants.JWT_ALGO_SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        // Access token usually last few min, so that they can use refresh token
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        // Issuer - can be company name / author
                        .withIssuer(request.getRequestURL().toString())
                        // roles
                        .withClaim("roles", user.getAuthorities())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error refreshing token: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                // response.sendError(FORBIDDEN.value());
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            // let the request continue if not "Bearer "
            throw new RuntimeException("Refresh token is missing");
        }
    }

}

@Data
class RoleToUserForm {
    private String username;
    private String authorities;
}

@Data
class UpdateUserForm {
    private String name;
    private String username;
    private String email;
    private ArrayList<String> authorities;
}
