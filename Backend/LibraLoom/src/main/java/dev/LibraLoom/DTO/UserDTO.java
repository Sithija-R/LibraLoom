package dev.LibraLoom.DTO;

import java.util.ArrayList;
import java.util.List;

import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Models.Transaction;
import lombok.Data;

@Data
public class UserDTO {
    

private String userId;
private String name;
private String email;

private Book borrowedBook;
private Transaction incompleteTransaction;
private List<Book> reservedBook = new ArrayList<>();
}
