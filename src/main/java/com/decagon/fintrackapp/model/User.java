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
import java.util.stream.Collectors;

@Getter
@Setter
@Table(name = ("users"))
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;

    /**
     * FK to the department table
     */

    @ManyToOne
    private Department department;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    @OneToMany
    private  List<Approval> approvals;
}
