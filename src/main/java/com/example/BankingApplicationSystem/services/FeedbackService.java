package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Feedback;
import com.example.BankingApplicationSystem.repositorys.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback submitFeedback(String accountId, String message) {
        Feedback feedback = new Feedback();
        feedback.setAccountId(accountId);
        feedback.setMessage(message);
        feedback.setCreatedAt(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByAccountId(String accountId) {
        return feedbackRepository.findByAccountId(accountId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}