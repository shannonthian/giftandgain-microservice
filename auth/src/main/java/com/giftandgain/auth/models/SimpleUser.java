package com.giftandgain.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser {
    private String name;
    private String username;
    private String password;
    private String email;
    private Collection<String> authorities = new ArrayList<>();
}
