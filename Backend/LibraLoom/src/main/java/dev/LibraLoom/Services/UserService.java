package dev.LibraLoom.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.LibraLoom.AppConfiguration.JwtProvider;
import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Repositories.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

     @Autowired
    private LibraryRepo libraryRepo;

    //get all
    public List<Users> findAll(){
        return userRepo.findAll();
    }

    // find by id
    public Users findUserByID(String userId) {
        return userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // find by email
    public Users findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
//find by name
    public List<Users> findUserByName(String name) {
        return userRepo.findByNameContainingIgnoreCase(name);
    }

    // find by jwt token
    public Users findByJwtToken(String jwt) {
        String email = jwtProvider.getEmailFromToken(jwt);

        return userRepo.findByEmail(email);
    }

    // find by email for authentication
    public User findbyemail(String email) {

        Users user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not found");

        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getEmail(), user.getPassword(), authorities);

    }

    //edit profile
    public Users updateUser(Users requester, String jwt ){
       
        Users user = findByJwtToken(jwt);
     
         if (requester.getName()!="") {
             user.setName(requester.getName());
         }
          userRepo.save(user);


          Library library = libraryRepo.findById("library01")
          .orElseThrow(() -> new RuntimeException("Library not found"));

  // Find the user in the library's list and update the details
  library.getListofUsers().stream()
          .filter(libraryUser -> libraryUser.getUserId().equals(user.getUserId())) // Match the user by ID
          .forEach(libraryUser -> libraryUser.setName(user.getName()));     // Update name

  // Save the updated library
  libraryRepo.save(library);



          return user;
     }

     // delete user
     public void deleteUser(String userId, String jwt) throws UserException {
      
        Users user = findUserByID(userId);
        Users reqUser = findByJwtToken(jwt);

        if (reqUser.getRole().equals("ADMIN") || reqUser.equals(user)) {
            // Find the library
            Library library = libraryRepo.findById("library01")
                    .orElseThrow(() -> new RuntimeException("Library not found"));

            userRepo.delete(user);
            library.getListofUsers().remove(user);
            
            libraryRepo.save(library);
        } else {
            
            throw new UserException("Not authorized to delete this user");
        }
    }
     

}
