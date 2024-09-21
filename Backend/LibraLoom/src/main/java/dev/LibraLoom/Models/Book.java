package dev.LibraLoom.Models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import lombok.Data;

@Data
@Document(collection = "books")
public class Book {

@MongoId
private String bookID;

private String isbn;
private String title;
private String author;
private int publicationYear;
private boolean isAvailable;

private Users borrower;



    
}
