package dev.LibraLoom.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document(collection = "library")
public class Library {
    
    @MongoId
    private String id;

    private String name;
    private String location;


    private List<Book> listofBooks = new ArrayList<>();


    private List<Book> listofAvailableBooks = new ArrayList<>();

    
    private List<Users> listofUsers = new ArrayList<>();

    private List<Transaction> listofTransactions = new ArrayList<>();
}
