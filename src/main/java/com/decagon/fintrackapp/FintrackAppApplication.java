package com.decagon.fintrackapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class FintrackAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintrackAppApplication.class, args);
    }

    @Bean
    public AuditorAware <String> auditorAware(){
        return new WebSecurityAuditable();
    }
}
