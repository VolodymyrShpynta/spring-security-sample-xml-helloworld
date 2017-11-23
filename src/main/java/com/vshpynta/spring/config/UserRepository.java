package com.vshpynta.spring.config;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserRepository {

  public User loadUser(String username, String credentials) {
    return User.builder()
            .username(username)
            .roles(Arrays.asList("ROLE_ADMIN"))
            .build();
  }
}
