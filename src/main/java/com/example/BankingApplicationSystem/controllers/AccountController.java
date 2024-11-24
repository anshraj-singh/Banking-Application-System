package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    //! get all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccount(){
        List<Account> allAccount = accountService.findAllAccount();
        if(allAccount != null && !allAccount.isEmpty()){
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


    //! get by id
    @GetMapping("id/{myId}")
    public ResponseEntity<Account> getById(@PathVariable String myId){
        Optional<Account> getIdAccount = accountService.findByIdAccount(myId);
        if(getIdAccount.isPresent()){
            return new ResponseEntity<>(getIdAccount.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //! delete by id
    @DeleteMapping("id/{myId}")
    public ResponseEntity<Account> deleteById(@PathVariable String myId){
        accountService.deleteByIdAccount(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //! successfully deleted
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<Account> updateAccount(@PathVariable String myId,@RequestBody Account updatedaccount){
        Account old = accountService.findByIdAccount(myId).orElse(null);
        if(old != null){
            if(updatedaccount.getAccountHolderName() != null && !updatedaccount.getAccountHolderName().isEmpty()){
                old.setAccountHolderName(updatedaccount.getAccountHolderName());
            }else{
                old.setAccountHolderName(old.getAccountHolderName());
            }

            if(updatedaccount.getAccountPassword() != null && !updatedaccount.getAccountPassword().isEmpty()){
                old.setAccountPassword(updatedaccount.getAccountPassword());
            }else{
                old.setAccountPassword(old.getAccountPassword());
            }

            if(updatedaccount.getBalance() > 0){
                old.setBalance(updatedaccount.getBalance());
            }
            accountService.saveAccount(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
