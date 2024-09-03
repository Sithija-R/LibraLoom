package dev.LibraLoom.Models;

import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
public class Librarian {
    
    @MongoId
    private String id;

    private String name;
    private String email;
    private String password;

}
