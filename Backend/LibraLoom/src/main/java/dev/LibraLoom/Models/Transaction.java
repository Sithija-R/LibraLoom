package dev.LibraLoom.Models;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document(collection = "transactions")
public class Transaction {
    
    @MongoId
    private String transactionId;

    private String userId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int lateDates;
    private boolean isCompleted;


}
