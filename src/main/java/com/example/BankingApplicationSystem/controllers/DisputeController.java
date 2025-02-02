package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Dispute;
import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.services.DisputeService;
import com.example.BankingApplicationSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disputes")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @Autowired
    private AccountService accountService; // Inject AccountService to retrieve account details

    // Report a new dispute
    @PostMapping
    public ResponseEntity<Dispute> reportDispute(@RequestParam String transactionId, @RequestParam String reason) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();

        // Retrieve account ID by username
        Account account = accountService.findByUserName(accountHolderName);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Dispute dispute = disputeService.reportDispute(transactionId, account.getId(), reason);
        return new ResponseEntity<>(dispute, HttpStatus.CREATED);
    }

    // Get all disputes for the authenticated user
    @GetMapping("/me")
    public ResponseEntity<List<Dispute>> getMyDisputes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();

        // Retrieve account ID by username
        Account account = accountService.findByUserName(accountHolderName);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Dispute> disputes = disputeService.getDisputesByAccountId(account.getId());
        return new ResponseEntity<>(disputes, HttpStatus.OK);
    }

    // Update dispute status
    @PutMapping("/{disputeId}")
    public ResponseEntity<Dispute> updateDisputeStatus(@PathVariable String disputeId, @RequestParam String status) {
        try {
            Dispute updatedDispute = disputeService.updateDisputeStatus(disputeId, status);
            return new ResponseEntity<>(updatedDispute, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Dispute not found
        }
    }

    // Resolve a dispute
    @PutMapping("/{disputeId}/resolve")
    public ResponseEntity<Dispute> resolveDispute(@PathVariable String disputeId) {
        try {
            Dispute resolvedDispute = disputeService.resolveDispute(disputeId);
            return new ResponseEntity<>(resolvedDispute, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Dispute not found
        }
    }

    // Close a dispute
    @PutMapping("/{disputeId}/close")
    public ResponseEntity<Dispute> closeDispute(@PathVariable String disputeId) {
        try {
            Dispute closedDispute = disputeService.closeDispute(disputeId);
            return new ResponseEntity<>(closedDispute, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Dispute not found
        }
    }
}