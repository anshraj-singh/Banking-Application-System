package com.example.BankingApplicationSystem.controllers;
import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //! get all accounts
    // Get all accounts (for admin or testing purposes, consider restricting this)
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccount() {
        List<Account> allAccount = accountService.findAllAccount();
        if (allAccount != null && !allAccount.isEmpty()) {
            return new ResponseEntity<>(allAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //! create accounts
    @PostMapping
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) {
        try {
            accountService.saveAccount(account);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get account by authenticated user
    @GetMapping("/me")
    public ResponseEntity<Account> getMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName(); // Get the authenticated user's username
        Optional<Account> account = Optional.ofNullable(accountService.findByUserName(accountHolderName));
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete account by authenticated user
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();// Get the authenticated user's username
        Optional<Account> account = Optional.ofNullable(accountService.findByUserName(accountHolderName));
        if(account.isPresent()){
            accountService.deleteByIdAccount(account.get().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Successfully deleted
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update account by authenticated user
    @PutMapping("/me")
    public ResponseEntity<Account> updateMyAccount(@RequestBody Account updatedAccount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName(); // Get the authenticated user's username

        Account oldAccount = accountService.findByUserName(accountHolderName);
        if (oldAccount != null) {
            if (updatedAccount.getAccountHolderName() != null && !updatedAccount.getAccountHolderName().isEmpty()) {
                oldAccount.setAccountHolderName(updatedAccount.getAccountHolderName());
            }

            if (updatedAccount.getAccountPassword() != null && !updatedAccount.getAccountPassword().isEmpty()) {
                oldAccount.setAccountPassword(updatedAccount.getAccountPassword());
            }

            if (updatedAccount.getBalance() >= 0) {
                oldAccount.setBalance(updatedAccount.getBalance());
            }

            accountService.saveAccount(oldAccount);
            return new ResponseEntity<>(oldAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}