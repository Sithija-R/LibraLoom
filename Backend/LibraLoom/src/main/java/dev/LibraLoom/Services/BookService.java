package dev.LibraLoom.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Repositories.BookRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public List<Book> getAllbooks() {
        return bookRepo.findAll();
    }

  

}
