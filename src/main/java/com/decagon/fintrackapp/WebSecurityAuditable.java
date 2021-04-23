package com.decagon.fintrackapp;

import com.decagon.fintrackapp.config.UserDetailImpl;
import com.decagon.fintrackapp.model.User;
import com.decagon.fintrackapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WebSecurityAuditable implements AuditorAware {
    @Autowired
    private UserRepository userRepository;
//    @Override
//    public Optional getCurrentAuditor() {
////        return Optional.ofNullable("Remi").filter(s-> !s.isEmpty());
//        return ((UserDetailImpl) authentication.getPrincipal()).getUser();
//    }
@Override
    public Optional getCurrentAuditor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return Optional.empty();
        }

    log.info(authentication.getPrincipal().toString());
    UserDetailImpl str = (UserDetailImpl) authentication.getPrincipal();
    String userName = str.getUsername();

    return Optional.ofNullable(userName);
    }
}
