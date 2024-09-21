package dev.LibraLoom.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
public class Library {
    
    @MongoId
    private String id;

    private String name;
    private String location;

    private List<Book> listofBooks = new ArrayList<>();
    private List<Users> listofUsers = new ArrayList<>();
}
