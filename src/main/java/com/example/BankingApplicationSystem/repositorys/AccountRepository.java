package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByAccountHolderName(String username);
}
