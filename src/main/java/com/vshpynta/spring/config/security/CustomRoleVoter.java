package com.vshpynta.spring.config.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;

import java.util.Collection;


public class CustomRoleVoter extends RoleVoter {

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = super.vote(authentication, object, attributes);
        System.out.println("Custom voting result is:" + result);
        return result;
    }
}
