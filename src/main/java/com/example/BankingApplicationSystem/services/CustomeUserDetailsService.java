package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account accountHolderName = accountRepository.findByAccountHolderName(username);
        if(accountHolderName != null){
           return org.springframework.security.core.userdetails.User.builder()
                    .username(accountHolderName.getAccountHolderName())
                    .password(accountHolderName.getAccountPassword())
                   .roles(accountHolderName.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("user not found with username : " + username);
    }

}
