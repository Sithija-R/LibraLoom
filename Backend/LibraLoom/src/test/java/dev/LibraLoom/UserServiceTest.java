package dev.LibraLoom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.LibraLoom.AppConfiguration.JwtProvider;
import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Repositories.UserRepo;
import dev.LibraLoom.Services.UserService;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private LibraryRepo libraryRepo;

    private Users user;
    private Library library;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setUserId("user01");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("USER");

        library = new Library();
        library.setListofUsers(new ArrayList<>());
    }

    @Test
    public void testFindUserByID_Success() {
        when(userRepo.findById("user01")).thenReturn(Optional.of(user));

        Users foundUser = userService.findUserByID("user01");

        assertNotNull(foundUser);
        assertEquals("user01", foundUser.getUserId());
    }

    @Test
public void testFindUserByID_NotFound() {
    when(userRepo.findById("user02")).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
        userService.findUserByID("user02");
    });

    assertEquals("User not found", exception.getMessage());
}


@Test
public void testUpdateUser_Success() {
    String jwt = "dummyToken";
    when(jwtProvider.getEmailFromToken(jwt)).thenReturn("test@example.com");
    when(userRepo.findByEmail("test@example.com")).thenReturn(user);

    library.getListofUsers().add(user); 

    when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));

    Users requester = new Users();
    requester.setName("Updated Name");

    Users updatedUser = userService.updateUser(requester, jwt);

    assertEquals("Updated Name", updatedUser.getName());
    assertEquals("Updated Name", library.getListofUsers().get(0).getName());
}


    @Test
    public void testDeleteUser_Success() throws UserException {
        String jwt = "dummyToken";
        when(jwtProvider.getEmailFromToken(jwt)).thenReturn("test@example.com");
        when(userRepo.findByEmail("test@example.com")).thenReturn(user);
        when(userRepo.findById("user01")).thenReturn(Optional.of(user));
        when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));

        userService.deleteUser("user01", jwt);

        verify(userRepo, times(1)).delete(user);
        assertFalse(library.getListofUsers().contains(user));
    }

    @Test
    public void testDeleteUser_NotAuthorized() {
        String jwt = "dummyToken";
        Users reqUser = new Users();
        reqUser.setRole("USER");
        
        when(jwtProvider.getEmailFromToken(jwt)).thenReturn("test@example.com");
        when(userRepo.findByEmail("test@example.com")).thenReturn(reqUser);
        when(userRepo.findById("user01")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(UserException.class, () -> {
            userService.deleteUser("user01", jwt);
        });

        assertEquals("Not authorized to delete this user", exception.getMessage());
    }
}
