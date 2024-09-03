package dev.LibraLoom.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Repositories.TransactionRepo;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepo transactionRepo;
    
}
