package com.nashtech.ecommercialwebsite.services.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.ecommercialwebsite.data.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private int id;

    private String username; //email

    private boolean isEnabled;

    private boolean isNonLocked;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(int id, String username, String password,
                           boolean isEnabled, boolean isNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isNonLocked = isNonLocked;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Account user) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if(user.getRole().getRoleName().equalsIgnoreCase("admin"))
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(user.getRole().getRoleName().equalsIgnoreCase("user"))
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getIsNonLocked(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
