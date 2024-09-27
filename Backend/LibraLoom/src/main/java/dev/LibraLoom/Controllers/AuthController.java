package dev.LibraLoom.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.LibraLoom.AppConfiguration.JwtProvider;
import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Repositories.UserRepo;
import dev.LibraLoom.Response.AuthResponse;
import dev.LibraLoom.Services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LibraryRepo libraryRepo;

    // create account -> params(email,password,name)
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody Users reqUser) throws UserException {

 

        String email = reqUser.getEmail();
        String password = reqUser.getPassword();
        String fullName = reqUser.getName();
        String role = reqUser.getRole();

        if ((userService.findUserByEmail(email) != null)) {
            throw new UserException("Email is already exist");
        }

        Library library = libraryRepo.findById("library01")
                .orElseThrow(() -> new RuntimeException("Library not found"));

        String encodedPassword = passwordEncoder.encode(password);
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setName(fullName);
        user.setRole(role);
        userRepo.save(user);
        library.getListofUsers().add(user);
        libraryRepo.save(library);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
    }

    // login reqUser -> params(email,password+)
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Users user) throws UserException {

        try {
            String email = user.getEmail();
            String password = user.getPassword();

            Authentication authentication = authenticate(email, password);
            String token = jwtProvider.generateToken(authentication);
            AuthResponse response = new AuthResponse(token, true);

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new UserException("Invalid Email or Password"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserException("Invalid Email or Password"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails = userService.findbyemail(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Email or Password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Email or Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
