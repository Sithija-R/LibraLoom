package dev.LibraLoom.DTO;

import java.util.ArrayList;
import java.util.List;

import dev.LibraLoom.Models.Book;
import lombok.Data;

@Data
public class UserDTO {
    

private String userId;
private String name;
private String email;

private List<Book> borroweBooks= new ArrayList<>();
private List<Book> reservedBook = new ArrayList<>();
}
