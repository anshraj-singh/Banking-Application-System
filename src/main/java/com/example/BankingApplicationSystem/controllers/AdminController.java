package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Transaction;
import com.example.BankingApplicationSystem.services.AccountService;
import com.example.BankingApplicationSystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    //! get all accounts
    // Get all accounts (for admin or testing purposes, consider restricting this)
    @GetMapping("/all-users")
    public ResponseEntity<List<Account>> getAllAccountsForAdmin() {
        List<Account> allAccount = accountService.findAllAccount();
        if (allAccount != null && !allAccount.isEmpty()) {
            return new ResponseEntity<>(allAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody Account account){
        accountService.saveAdmin(account);
    }

    // Delete account by authenticated user
    @DeleteMapping("/admin/deleteAccount")
    public ResponseEntity<String> deleteMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account != null) {
            accountService.deleteByIdAccount(account.getId());
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
    }
}
