package com.decagon.fintrackapp.model;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class WebSecurityAuditable implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        return Optional.ofNullable("Remi").filter(s-> !s.isEmpty());
    }
}
