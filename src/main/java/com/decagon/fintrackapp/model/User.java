package com.decagon.fintrackapp.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table(name = ("users"))
@Entity
public class User extends BaseModel implements UserDetails {
    private String name;
    private String email;
    private String password;

    /**
     * FK to the department table
     */
    private String deptId;

    @Enumerated(EnumType.STRING)
    @ManyToMany
    private List<Role> eRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        eRoles.stream().map(role -> {
            role.getAuthorities().forEach(auth -> {
                authorities.add(new SimpleGrantedAuthority(auth));
            });
        });
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
