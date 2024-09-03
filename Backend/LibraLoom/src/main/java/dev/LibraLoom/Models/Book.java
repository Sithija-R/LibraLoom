package dev.LibraLoom.Models;

import org.springframework.data.mongodb.core.mapping.MongoId;
import lombok.Data;

@Data
public class Book {

@MongoId
private String bookID;

private String ISBN;
private String title;
private String author;
private int publicationYear;
private boolean isAvailable;



    
}
