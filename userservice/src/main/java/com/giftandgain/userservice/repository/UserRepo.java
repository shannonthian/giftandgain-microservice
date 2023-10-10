package com.giftandgain.userservice.repository;

import com.giftandgain.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}