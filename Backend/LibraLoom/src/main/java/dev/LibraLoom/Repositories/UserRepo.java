package dev.LibraLoom.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import dev.LibraLoom.Models.Users;

@Repository
public interface UserRepo extends MongoRepository<Users,String> {
    
    public Users findByEmail(String email);
    public List<Users> findByNameContainingIgnoreCase(String name);
}
