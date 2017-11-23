package com.vshpynta.spring.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String id;

    private String username;

    private String email;
    private String password;

    private String organization;
    private List<String> roles;
    private boolean active;
    @JsonIgnore
    private String invitationHash;


    public void erasePassword() {
        this.password = null;
    }

    public void activate(String password) {
        this.active = true;
        this.password = password;
        this.invitationHash = null;
    }

}
