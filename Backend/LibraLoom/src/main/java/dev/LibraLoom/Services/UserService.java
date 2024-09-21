package dev.LibraLoom.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;


public Users findUserByEmail(String email){
    return userRepo.findByEmail(email);
}

public User findbyemail(String email) {

        Users user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not found");

        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getEmail(), user.getPassword(), authorities);
     

    }

}
