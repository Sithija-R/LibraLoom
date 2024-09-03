package dev.LibraLoom.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.Book;

@Repository
public interface BookRepo extends MongoRepository<Book,String>{
    
}
