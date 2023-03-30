package com.security.demo.security;

import com.security.demo.model.Role;
import com.security.demo.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//spring security will provide a userdetails interface which we need to implement according to our needs
//spring security doesnt know our users object it only knows the implemented class of UserDetails so we need to pass our cusom
//user entity object as constructor parameter so that MyUserDetails is prepared
public class MyUserDetails implements UserDetails {

    private Users users;

    public MyUserDetails(Users users){
        this.users = users;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = users.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
