package com.vshpynta.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class GreetingController {


//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting() {
        return "greeting," + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello," + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
