package dev.LibraLoom.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
public class User {
    
@MongoId
private String userId;

private String name;
private String email;
private String password;

private List<Book> borroweBooks= new ArrayList<>();
private List<Book> reservedBook = new ArrayList<>();
}
