package dev.LibraLoom.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.Transaction;

@Repository
public interface TransactionRepo extends MongoRepository<Transaction,String>{
    
    public Transaction findByUniqueId(String uniqueId);
}
