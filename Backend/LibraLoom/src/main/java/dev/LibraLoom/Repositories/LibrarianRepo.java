package dev.LibraLoom.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.Librarian;

@Repository
public interface LibrarianRepo extends MongoRepository<Librarian,String> {
    
}
