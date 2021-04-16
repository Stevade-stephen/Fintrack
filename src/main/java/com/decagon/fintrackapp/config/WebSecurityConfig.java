package com.decagon.fintrackapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/auth/callback","/oauth2/**","/", "/login", "/h2-console").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login()
//                .defaultSuccessUrl("/home");
//    }



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        private final UserDetailsServiceImpl userServiceDetails;
        private final JwtRequestFilter jwtRequestFilter;

        @Autowired
        public WebSecurityConfig(UserDetailsServiceImpl userServiceDetails,
                                 JwtRequestFilter jwtRequestFilter){
            this.userServiceDetails = userServiceDetails;
            this.jwtRequestFilter = jwtRequestFilter;
        }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.httpBasic().and()
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/auth/callback","/oauth2/**","/", "/login", "/h2-console", "/swagger-ui/**", "/configuration/**",
                            "/swagger-resources/**", "/v2/api-docs","/webjars/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/home");
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userServiceDetails);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
