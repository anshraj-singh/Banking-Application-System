package com.example.BankingApplicationSystem.services;

import com.example.BankingApplicationSystem.entity.Dispute;
import com.example.BankingApplicationSystem.repositorys.DisputeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DisputeService {

    @Autowired
    private DisputeRepository disputeRepository;

    public Dispute reportDispute(String transactionId, String accountId, String reason) {
        Dispute dispute = new Dispute();
        dispute.setTransactionId(transactionId);
        dispute.setAccountId(accountId);
        dispute.setReason(reason);
        dispute.setStatus("PENDING");
        dispute.setCreatedAt(LocalDateTime.now());
        return disputeRepository.save(dispute);
    }

    public List<Dispute> getDisputesByAccountId(String accountId) {
        return disputeRepository.findByAccountId(accountId);
    }

    public Dispute updateDisputeStatus(String disputeId, String status) {
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new IllegalArgumentException("Dispute not found."));

        // Validate the status before updating
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status provided.");
        }

        dispute.setStatus(status);
        return disputeRepository.save(dispute);
    }

    // Method to automatically resolve disputes based on certain conditions
    public Dispute resolveDispute(String disputeId) {
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new IllegalArgumentException("Dispute not found."));

        // Logic to determine if the dispute can be resolved
        // For example, if the dispute has been reviewed or a certain time has passed
        // Here, we simply set it to "RESOLVED" for demonstration purposes
        dispute.setStatus("RESOLVED");
        return disputeRepository.save(dispute);
    }

    // Method to close a dispute
    public Dispute closeDispute(String disputeId) {
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new IllegalArgumentException("Dispute not found."));

        dispute.setStatus("CLOSED");
        return disputeRepository.save(dispute);
    }

    // Helper method to validate status
    private boolean isValidStatus(String status) {
        return status.equals("PENDING") || status.equals("RESOLVED") || status.equals("CLOSED");
    }
}