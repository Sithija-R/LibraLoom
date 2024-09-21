package dev.LibraLoom.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document(collection = "user")
public class Users {
    
@MongoId
private String userId;

private String name;
private String email;
private String password;

private List<Book> borroweBooks= new ArrayList<>();
private List<Book> reservedBook = new ArrayList<>();
}
