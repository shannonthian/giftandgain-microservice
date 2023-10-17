package com.giftandgain.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.giftandgain.userservice.constants.constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.giftandgain.userservice.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.giftandgain.userservice.repository.UserRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    // Lombok helps to create the constructors with @RequiredArgsConstructor
    private final UserRepo userRepo;
//    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        // need to return GrantedAuthority type for spring User model
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(role -> { authorities.add(new SimpleGrantedAuthority(role));
        });
//        user.getAuthorities().forEach(role -> { authorities.add(new SimpleGrantedAuthority(role.getName()));
//        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities );
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

//    @Override
//    public Authorities saveRole(Authorities authorities) {
//        log.info("Saving new role {} to the database", authorities.getName());
//        return roleRepo.update(authorities);
//    }

    @Override
    public void addRoleToUser(String username, String authorities) {
        log.info("Adding role {} to user {}", authorities, username );
        User user = userRepo.findByUsername(username);
        // Because we have @transactional, don't need to user.save again
        user.getAuthorities().add(authorities);
    }

    @Override
    public User getUser(String authToken) {
        String token = authToken.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(constants.JWT_ALGO_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

}
