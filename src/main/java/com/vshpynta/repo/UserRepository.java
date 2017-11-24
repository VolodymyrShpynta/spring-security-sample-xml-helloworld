package com.vshpynta.repo;

import com.vshpynta.model.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserRepository {

    public User loadUser(String username, String credentials) {
        if ("admin".equalsIgnoreCase(username)
                && "pass".equals(credentials)) {
            return User.builder()
                    .username(username)
                    .password(credentials)
                    .roles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"))
                    .build();
        }
        return null;

    }
}
