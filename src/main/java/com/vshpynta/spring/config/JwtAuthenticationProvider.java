package com.vshpynta.spring.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("jwtAuthenticationProvider")
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;


    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication) ||
                UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            return authentication instanceof JwtAuthenticationToken ?
                    getJwtAuthentication(((JwtAuthenticationToken) authentication).getToken()) :
                    getJwtAuthentication(getUser(authentication));
        } catch (RuntimeException | IOException e) {
            throw new InvalidAuthenticationException("Access denied", e);
        }
    }

    private Authentication getJwtAuthentication(User user) throws JsonProcessingException {
        user.erasePassword();
        String token = jwtUtil.generateToken(user);
        return new JwtAuthenticationToken(grantedAuthorities(user.getRoles()), user.getId(), user.getUsername(), token);
    }

    @SuppressWarnings("unchecked")
    private Authentication getJwtAuthentication(String token) {
        //this will fail if the token is tampered with
        User user = jwtUtil.parseToken(token);
        return new JwtAuthenticationToken(
                grantedAuthorities(user.getRoles()),
                user.getId(),
                user.getUsername(),
                token);
    }

    private List<SimpleGrantedAuthority> grantedAuthorities(List<String> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    private User getUser(Authentication authentication) throws IOException {
        return userRepository.loadUser(authentication.getName(), (String) authentication.getCredentials());
    }

}
