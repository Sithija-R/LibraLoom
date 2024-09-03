package dev.LibraLoom.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Repositories.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;
}
