package com.decagon.fintrackapp.model;

import com.decagon.fintrackapp.config.UserDetailImpl;
import com.decagon.fintrackapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WebSecurityAuditable implements AuditorAware {
    @Autowired
    private UserRepository userRepository;
//    @Override
//    public Optional getCurrentAuditor() {
////        return Optional.ofNullable("Remi").filter(s-> !s.isEmpty());
//        return ((UserDetailImpl) authentication.getPrincipal()).getUser();
//    }

    public Optional getCurrentAuditor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return Optional.empty();
        }
        return Optional.ofNullable(userRepository.findByName(authentication.getName()).getName());
    }
}
