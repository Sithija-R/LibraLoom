package dev.LibraLoom.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.Library;

@Repository
public interface LibraryRepo extends MongoRepository<Library,String> {
    
}
