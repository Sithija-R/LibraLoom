package dev.LibraLoom.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.LibraLoom.Models.User;

@Repository
public interface UserRepo extends MongoRepository<User,String> {
    
}
