package dev.LibraLoom.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.Book;

@Repository
public interface BookRepo extends MongoRepository<Book,String>{

    public Book findByIsbn(String isbn);
    public List<Book> findByTitle(String title);
    public List<Book> findByAuthor(String author);
    public List<Book> findByTitleContainingIgnoreCase(String title);
    public List<Book> findByAuthorContainingIgnoreCase(String author);

    
}