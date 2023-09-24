package com.giftandgain.userservice.service;

import com.giftandgain.userservice.models.Role;
import com.giftandgain.userservice.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
