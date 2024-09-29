package dev.LibraLoom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Repositories.UserRepo;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Services.UserService;
import dev.LibraLoom.AppConfiguration.JwtProvider;
import dev.LibraLoom.Exception.UserException;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LibraryRepo libraryRepo;

    @Autowired
    private JwtProvider jwtProvider;

    // Sample test data
    private final String testUserId = "123456";
    private final String testEmail = "johndoe@example.com";
    private final String testPassword = "password123";

    private final String testUserId2 = "1234567";
    private final String testEmail2 = "jane@example.com";
    private final String testPassword2 = "password123";

    // Setup for creating test data
    @BeforeEach
    public void setUp() {
        userRepo.deleteAll();
    
        // First user
        Users user = new Users();
        user.setUserId(testUserId);
        user.setName("John Doe");
        user.setEmail(testEmail);
        user.setPassword(testPassword);
        user.setRole("USER");
        userRepo.save(user); // Save the first user
    
        // Second user
        Users user2 = new Users(); // Create a new instance for the second user
        user2.setUserId(testUserId2);
        user2.setName("Jane Doe");
        user2.setEmail(testEmail2);
        user2.setPassword(testPassword2);
        user2.setRole("USER");
        userRepo.save(user2); // Save the second user
    }
    

    // Method to generate JWT token for test
    private String generateJwtToken(String email, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =jwtProvider.generateToken(authentication);
        return token;
    }

    // Test findAll method
    @Test
    public void testFindAllUsers() {
        List<Users> users = userService.findAll();
        int expectedSize = 2;
        assertNotNull(users, "Users list should not be null");
        assertFalse(users.isEmpty(), "Users list should not be empty");
        assertEquals(expectedSize, users.size(), "Expected size: " + expectedSize + ", but got: " + users.size());
    }

    // Test findUserByID when user exists
    @Test
    public void testFindUserByID_UserExists() {
        Users user = userService.findUserByID(testUserId);
        String expectedName = "John Doe";
        assertNotNull(user, "User should not be null");
        assertEquals(testUserId, user.getUserId(), "Expected ID: " + testUserId + ", but got: " + user.getUserId());
        assertEquals(expectedName, user.getName(), "Expected name: " + expectedName + ", but got: " + user.getName());
    }

    // Test findUserByID when user does not exist
    @Test
    public void testFindUserByID_UserNotFound() {
        String invalidUserId = "999999";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.findUserByID(invalidUserId);
        });
        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Expected message: " + expectedMessage + ", but got: " + actualMessage);
    }

    // Test findUserByEmail when user exists
    @Test
    public void testFindUserByEmail_UserExists() {
        Users user = userService.findUserByEmail(testEmail);
        assertNotNull(user, "User should not be null");
        assertEquals(testEmail, user.getEmail(), "Expected email: " + testEmail + ", but got: " + user.getEmail());
    }

    // Test findUserByEmail when user does not exist
    @Test
    public void testFindUserByEmail_UserNotFound() {
        String invalidEmail = "invalid@example.com";
        Users user = userService.findUserByEmail(invalidEmail);
        assertNull(user, "User should be null for non-existent email");
    }

    // Test findByJwtToken method (using generated JWT token)
    @Test
    public void testFindByJwtToken_Valid() {
       
        String token = generateJwtToken(testEmail, testPassword);
        String bearerToken = "Bearer " + token;

        Users user = userService.findByJwtToken(bearerToken);
 
        assertNotNull(user, "User should not be null");
        assertEquals(testEmail, user.getEmail(), "Expected email: " + testEmail + ", but got: " + user.getEmail());
    }
    

    // Test findbyemail for authentication
    @Test
    public void testFindbyemail_UserExists() {
        String token = generateJwtToken(testEmail, testPassword);
        org.springframework.security.core.userdetails.User authUser = userService.findbyemail(testEmail);

        assertNotNull(authUser, "Authenticated user should not be null");
        assertEquals(testEmail, authUser.getUsername(), "Expected username: " + testEmail + ", but got: " + authUser.getUsername());
    }

    @Test
    public void testFindbyemail_UserNotFound() {
        String invalidEmail = "invalid@example.com";
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.findbyemail(invalidEmail);
        });
    }

    // Test updateUser method
    @Test
    public void testUpdateUser_ProfileUpdated() {
        String expectedUpdatedName = "Jane Doe";
        Users requester = new Users();
        requester.setName(expectedUpdatedName);

        String token = generateJwtToken(testEmail, testPassword);
        String bearerToken = "Bearer " + token;
        Users updatedUser = userService.updateUser(requester,bearerToken);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(expectedUpdatedName, updatedUser.getName(), "Expected name: " + expectedUpdatedName + ", but got: " + updatedUser.getName());

        Library library = libraryRepo.findById("library01").orElse(null);
        assertNotNull(library, "Library should not be null");
        assertFalse(library.getListofUsers().stream()
                .anyMatch(u -> u.getName().equals(expectedUpdatedName)), "Library should update user name to " + expectedUpdatedName);
    }

    // Test deleteUser
    // @Test
    // public void testDeleteUser_AsSelf() throws UserException {
    //     String token = generateJwtToken(testEmail, testPassword);
    //     String bearerToken = "Bearer " + token;
    //     userService.deleteUser(testUserId, bearerToken);

    //     boolean userExists = userRepo.existsById(testUserId);
    //     assertFalse(userExists, "User should be deleted");

    //     Library library = libraryRepo.findById("library01").orElse(null);
    //     assertNotNull(library, "Library should not be null");
    //     assertFalse(library.getListofUsers().isEmpty(), "Library should no longer have the user");
    // }

    // Test deleteUser when not authorized
    @Test
    public void testDeleteUser_NotAuthorized() throws UserException {
        String differentJwtToken = generateJwtToken(testEmail2, testPassword2);
        String bearerToken = "Bearer " + differentJwtToken;
        UserException exception = assertThrows(UserException.class, () -> {
            userService.deleteUser(testUserId, bearerToken);
        });

        String expectedMessage = "Not authorized to delete this user";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Expected message: " + expectedMessage + ", but got: " + actualMessage);
    }
}
