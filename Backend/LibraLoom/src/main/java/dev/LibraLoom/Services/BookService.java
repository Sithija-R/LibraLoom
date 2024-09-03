package dev.LibraLoom.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Repositories.BookRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;
    
}
