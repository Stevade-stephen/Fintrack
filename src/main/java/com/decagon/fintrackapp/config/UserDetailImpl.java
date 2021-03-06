package com.decagon.fintrackapp.config;
import com.decagon.fintrackapp.model.Department;
import com.decagon.fintrackapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class UserDetailImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private  String username;
    private  String password;
    private Department department;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public UserDetailImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> grantedAuthorities, Department department) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
        this.department = department;
    }

    public static UserDetails buildUserDetail(User user) {
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getAppUserRole().name()))
                .collect(Collectors.toList());
        log.info(String.valueOf(grantedAuthorities));
        log.info(String.valueOf(user));
        return new UserDetailImpl(
                user.getId(),
                user.getName(),
                user.getPassword(),
                grantedAuthorities,
                user.getDepartment()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
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
//    private Long id;
//    private  String username;
//    private  String password;
//    private User user;
//    private Collection<? extends GrantedAuthority> grantedAuthorities;
//
//    public UserDetailImpl(User user) {
//        this(user, new ArrayList<GrantedAuthority>());
//    }
//
//    public UserDetailImpl(User user, Collection<? extends GrantedAuthority> grantedAuthorities) {
//        this.user = user;
//        this.grantedAuthorities = grantedAuthorities;
//    }
//
//    public UserDetailImpl(Long id, String name, String password, List<GrantedAuthority> grantedAuthorities) {
//        this.id = id;
//        this.username = name;
//        this.password = password;
//
//    }
//
//    public static UserDetails buildUserDetail(User user) {
//        List<GrantedAuthority> grantedAuthorities = user.getRoles()
//                .stream()
//                .map(userRole -> new SimpleGrantedAuthority(userRole.getAppUserRole().name()))
//                .collect(Collectors.toList());
//        return new UserDetailImpl(
//                user.getId(),
//                user.getName(),
//                user.getPassword(),
//                grantedAuthorities
//        );
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return grantedAuthorities;
//    }
//    @Override
//    public String getPassword() {
//        return password;
//    }
//    @Override
//    public String getUsername() {
//        return username;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//    public User getUser() {
//        return user;
//    }

}
