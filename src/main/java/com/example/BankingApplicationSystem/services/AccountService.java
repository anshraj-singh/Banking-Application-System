package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.repositorys.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account){
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
