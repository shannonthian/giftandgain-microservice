package com.giftandgain.auth.repository;

import com.giftandgain.auth.models.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Authorities, Long> {
    Authorities findByName(String name);
}
