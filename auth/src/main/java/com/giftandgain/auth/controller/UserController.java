package com.giftandgain.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgain.auth.models.Authorities;
import com.giftandgain.auth.models.SimpleUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import com.giftandgain.auth.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.giftandgain.auth.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @GetMapping("/account")
    public ResponseEntity <SimpleUser> getUser() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Assuming your authentication object includes user information
        String username = authentication.getName();

        if (!StringUtils.hasText(username)) {
            return ResponseEntity.unprocessableEntity().body(null);
        }

        User retrievedUser = userService.getUser(username);

        if (retrievedUser == null) {
            return ResponseEntity.ok().body(null);
        }

        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setUsername(retrievedUser.getUsername());
        simpleUser.setEmail(retrievedUser.getEmail());
        simpleUser.setAuthorities(retrievedUser.getAuthorities()
                .stream()
                .map(Authorities::getName)
                .collect(Collectors.toList()));

        return ResponseEntity.ok().body(simpleUser);
    }

    @GetMapping("/users")
    public ResponseEntity <List<User>> getUsers() {
        return  ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity <User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return  ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity <Authorities> saveRole(@RequestBody Authorities authorities) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return  ResponseEntity.created(uri).body(userService.saveRole(authorities));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity <?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                // TODOBEN - refactor the "secret"
                // TODOBEN - refactor algorithm?
                // need to ensure that the sign and verify both same algo
                // JWT Refresh Token - so typically, BE send back the tokens
                // frontend need to store the token on client side
                // send request with access token
                // usually is wait till access token expire, receive the expired token error msg
                // from BE, then use refresh token send request
                // get another access token
                // seamless experience - user doesnt know token expired, requested new token
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
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
                        .withArrayClaim("roles", user.getAuthorities().toArray(new String[0]))
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
    private String roleName;
}

@Data
class AccountForm {
    private String username;
}
