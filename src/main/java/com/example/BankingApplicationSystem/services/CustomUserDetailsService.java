package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountHolderName = accountRepository.findByAccountHolderName(username);
        if(accountHolderName.isPresent()){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(accountHolderName.get().getAccountHolderName())
                    .password(accountHolderName.get().getAccountPassword())
                    .roles("USER") // Assign a default role
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
