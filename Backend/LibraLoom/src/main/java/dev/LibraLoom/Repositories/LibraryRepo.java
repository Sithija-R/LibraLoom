package dev.LibraLoom.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.Library;

@Repository
public interface LibraryRepo extends MongoRepository<Library,String> {
    
    public Optional<Library> findById(String id);
   
}
