package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Account saveAccount(Account account){
        account.setAccountPassword(passwordEncoder.encode(account.getAccountPassword()));
        return accountRepository.save(account);
    }

    public List<Account> findAllAccount(){
        return accountRepository.findAll();
    }

    public Optional<Account> findByIdAccount(String myId){
        return accountRepository.findById(myId);
    }

    public void deleteByIdAccount(String myId){
        accountRepository.deleteById(myId);
    }

}
