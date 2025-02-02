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
        dispute.setStatus(status);
        return disputeRepository.save(dispute);
    }
}