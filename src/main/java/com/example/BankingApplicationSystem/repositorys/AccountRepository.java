package com.example.BankingApplicationSystem.repositorys;

import com.example.BankingApplicationSystem.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String> {

}
