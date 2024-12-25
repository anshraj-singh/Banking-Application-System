package com.example.BankingApplicationSystem.controllers;

import com.example.BankingApplicationSystem.entity.Account;
import com.example.BankingApplicationSystem.entity.Feedback;
import com.example.BankingApplicationSystem.services.AccountService;
import com.example.BankingApplicationSystem.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private AccountService accountService;

    // Submit feedback
    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestParam String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();

        // Retrieve the account based on the username
        Account account = accountService.findByUserName(accountHolderName);

        // Submit feedback
        Feedback feedback = feedbackService.submitFeedback(account.getId(), message);
        return new ResponseEntity<>("Feedback submitted successfully. ID: " + feedback.getId(), HttpStatus.CREATED);
    }

    // Get feedback for authenticated user
    @GetMapping("/me")
    public ResponseEntity<List<Feedback>> getMyFeedback() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountHolderName = authentication.getName();

        // Retrieve the account based on the username
        Account account = accountService.findByUserName(accountHolderName);

        // Get feedback for the account
        List<Feedback> feedbackList = feedbackService.getFeedbackByAccountId(account.getId());
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }

    // Get all feedback (admin only)
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
}