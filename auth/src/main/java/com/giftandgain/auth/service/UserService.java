package com.giftandgain.auth.service;

import com.giftandgain.auth.models.Authorities;
import com.giftandgain.auth.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Authorities saveRole(Authorities authorities);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
