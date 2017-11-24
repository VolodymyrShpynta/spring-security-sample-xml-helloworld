package com.vshpynta.spring.config.security;

import lombok.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private AuthenticationManager authenticationManager;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String authToken = request.getHeader("X-AUTH-TOKEN");
    if (authToken == null) {
      chain.doFilter(request, response);
      return;
    }
    Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(authToken));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }
}
