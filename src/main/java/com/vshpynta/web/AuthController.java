package com.vshpynta.web;

import com.vshpynta.model.User;
import com.vshpynta.repo.UserRepository;
import com.vshpynta.spring.config.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        User loadedUser = userRepository.loadUser(user.getUsername(), user.getPassword());
        if (loadedUser != null) {
            return jwtUtil.generateToken(loadedUser);
        }
        return "Try again";
    }
}
