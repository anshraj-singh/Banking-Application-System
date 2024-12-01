package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Account saveAccount(Account account) {
        account.setAccountPassword(passwordEncoder.encode(account.getAccountPassword()));
        account.setCreatedAt(LocalDateTime.now()); // Set the current date and time
        account.setRoles(List.of("USER"));
        return accountRepository.save(account);
    }

    public List<Account> findAllAccount() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByIdAccount(String myId) {
        return accountRepository.findById(myId);
    }

    public void deleteByIdAccount(String myId) {
        accountRepository.deleteById(myId);
    }

    public Account findByUserName(String userName){
        return accountRepository.findByAccountHolderName(userName);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}