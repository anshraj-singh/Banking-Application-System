package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class Admin {

    @Autowired
    private AccountService accountService;

    //! get all accounts
    // Get all accounts (for admin or testing purposes, consider restricting this)
    @GetMapping("/admin")
    public ResponseEntity<List<Account>> getAllAccountsForAdmin() {
        List<Account> allAccount = accountService.findAllAccount();
        if (allAccount != null && !allAccount.isEmpty()) {
            return new ResponseEntity<>(allAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
