package com.vshpynta.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationEventPublisher(new NoopAuthenticationEventPublisher())
                .authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterBefore(new BasicAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
            .requiresChannel()
                // enable https only for login
                .antMatchers("/rest/auth/login*").requiresSecure()
                .anyRequest().requiresInsecure()
                .and()
            .authorizeRequests()
                // needed for CORS
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                .antMatchers("/rest/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/rest/auth/login").permitAll()
                .antMatchers("/**").authenticated();
        //@formatter:on
    }

}
