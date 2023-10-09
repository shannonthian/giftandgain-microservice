package com.giftandgain.userservice.repository;

import com.giftandgain.userservice.models.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Authorities, Long> {
    Authorities findByName(String name);
}
