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
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

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
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Delete account by authenticated user
    @DeleteMapping("/me")
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

    // Update account by authenticated user
    @PutMapping("/me")
    public ResponseEntity<String> updateMyAccount(@RequestBody Account updatedAccount) {
        // Retrieve the authenticated user's account holder name
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account existingAccount = accountService.findByUserName(accountHolderName);

        if (existingAccount == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        // Update fields if they are provided
        if (updatedAccount.getAccountHolderName() != null && !updatedAccount.getAccountHolderName().isEmpty()) {
            // Check if the new account holder name already exists (excluding the current user)
            Account existingAccountWithName = accountService.findByUserName(updatedAccount.getAccountHolderName());
            if (existingAccountWithName != null && !existingAccountWithName.getId().equals(existingAccount.getId())) {
                return new ResponseEntity<>("Account holder name already exists", HttpStatus.CONFLICT);
            }
            existingAccount.setAccountHolderName(updatedAccount.getAccountHolderName());
        }

        if (updatedAccount.getAccountPassword() != null && !updatedAccount.getAccountPassword().isEmpty()) {
            existingAccount.setAccountPassword(
                    accountService.getPasswordEncoder().encode(updatedAccount.getAccountPassword())
            );
        }

        if (updatedAccount.getAccountType() != null && !updatedAccount.getAccountType().isEmpty()) {
            existingAccount.setAccountType(updatedAccount.getAccountType());
        }

        if (updatedAccount.getBalance() >= 0) {
            existingAccount.setBalance(updatedAccount.getBalance());
        }

        // Save the updated account
        accountService.saveAccount(existingAccount);

        return new ResponseEntity<>("Account updated successfully", HttpStatus.OK);
    }

    //! Deposit money into account
    @PostMapping("/me/deposit")
    public ResponseEntity<String> deposit(@RequestParam double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        Transaction transaction = transactionService.deposit(account, amount);
        return new ResponseEntity<>("Deposit successful. Transaction ID: " + transaction.getId(), HttpStatus.OK);
    }

    //! Withdraw money from account
    @PostMapping("/me/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }

        try {
            Transaction transaction = transactionService.withdraw(account, amount);
            return new ResponseEntity<>("Withdrawal successful. Transaction ID: " + transaction.getId(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //! Get all transactions for authenticated user
    @GetMapping("/me/transactions")
    public ResponseEntity<List<Transaction>> getMyTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();
        Account account = accountService.findByUserName(accountHolderName);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Transaction> transactions = transactionService.findTransactionsByAccountId(account.getId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}