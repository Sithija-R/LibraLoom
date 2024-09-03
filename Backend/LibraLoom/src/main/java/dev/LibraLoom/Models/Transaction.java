package dev.LibraLoom.Models;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
public class Transaction {
    
    @MongoId
    private String transactionId;

    private String userId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

}
