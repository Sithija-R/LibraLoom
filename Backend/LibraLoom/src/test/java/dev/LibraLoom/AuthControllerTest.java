package dev.LibraLoom;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Repositories.UserRepo;
import dev.LibraLoom.Services.UserService;
import dev.LibraLoom.AppConfiguration.JwtProvider;
import dev.LibraLoom.Controllers.AuthController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepo userRepo;

    @Mock
    private LibraryRepo libraryRepo;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    // Positive test case for user signup
    @Test
    public void testUserSignup() throws Exception {
        Users reqUser = new Users();
        reqUser.setEmail("test@example.com");
        reqUser.setPassword("password123");
        reqUser.setName("Test User1");
        reqUser.setRole("USER");

        when(userService.findUserByEmail("test@example.com")).thenReturn(null);
        when(libraryRepo.findById("library01")).thenReturn(java.util.Optional.of(new Library()));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepo.save(any(Users.class))).thenReturn(reqUser);
        when(jwtProvider.generateToken(any())).thenReturn("jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jwt").value("jwtToken"))
                .andExpect(jsonPath("$.status").value(true));
    }

    // Negative test case for user signup (email already exists)
    @Test
    public void testUserSignupEmailAlreadyExists() throws Exception {
        Users reqUser = new Users();
        reqUser.setEmail("test@example.com");
        reqUser.setPassword("password123");
        reqUser.setName("Test User");
        reqUser.setRole("USER");
    
        when(userService.findUserByEmail("test@example.com")).thenReturn(reqUser);
    
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email is already exist"))
                .andExpect(jsonPath("$.status").value(false));
    }
    

    // Positive test case for user login
    @Test
    public void testUserLogin() throws Exception {
        Users loginUser = new Users();
        loginUser.setEmail("test@example.com");
        loginUser.setPassword("password123");

        User springUser = new User(loginUser.getEmail(), "encodedPassword", new ArrayList<>());

        when(userService.findbyemail("test@example.com")).thenReturn(springUser);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtProvider.generateToken(any())).thenReturn("jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.jwt").value("jwtToken"))
                .andExpect(jsonPath("$.status").value(true));
    }

    // Negative test case for user login (incorrect password)
    @Test
    public void testUserLoginWithCorrectEmailButIncorrectPassword() throws Exception {
        Users loginUser = new Users();
        loginUser.setEmail("test@example.com");
        loginUser.setPassword("wrongPassword");

        User existingUser = new User("test@example.com", "encodedPassword", new ArrayList<>());

        when(userService.findbyemail("test@example.com")).thenReturn(existingUser);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                // .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid Email or Password"));
    }

    // Negative test case for user login (user not found)
    @Test
    public void testUserLoginUserNotFound() throws Exception {
        Users loginUser = new Users();
        loginUser.setEmail("nonexistent@example.com");
        loginUser.setPassword("password123");

        when(userService.findbyemail("nonexistent@example.com")).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                // .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid Email or Password"));
    }
}
