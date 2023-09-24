package com.giftandgain.userservice.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
//PostgreSQL got error with "User" as it is reserved, need change to users
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    // this loads the roles each time a user is loaded
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
