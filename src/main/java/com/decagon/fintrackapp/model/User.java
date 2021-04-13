package com.decagon.fintrackapp.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Table(name = ("users"))
@Entity
public class User extends Auditable<String>{
    private String name;
    private String email;
    private String password;

    public User() {
    }

    /**
     * FK to the department table
     */

    @ManyToOne
    private Department department;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    @OneToMany
    private  List<Approval> approvals;
}
