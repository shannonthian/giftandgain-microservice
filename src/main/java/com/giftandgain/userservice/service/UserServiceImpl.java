package com.giftandgain.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.giftandgain.userservice.models.Role;
import com.giftandgain.userservice.models.User;
import org.springframework.stereotype.Service;
import com.giftandgain.userservice.repository.RoleRepo;
import com.giftandgain.userservice.repository.UserRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    // Lombok helps to create the constructors with @RequiredArgsConstructor
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username );
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        // Because we have @transactional, don't need to user.save again
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
