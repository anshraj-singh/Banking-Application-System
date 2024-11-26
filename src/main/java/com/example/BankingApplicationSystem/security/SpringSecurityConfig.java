package com.example.BankingApplicationSystem.security;

import com.example.BankingApplicationSystem.services.CustomeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomeUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/accounts/me/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic(); // Enable Basic Authentication

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless session for REST APIs
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //! hare is work on user and password related authenticate
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //! to match database pass and login password
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //! take password then change hash form create password
        return new BCryptPasswordEncoder();
    }
}
