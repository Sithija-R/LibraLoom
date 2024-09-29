package dev.LibraLoom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.LibraLoom.Controllers.UserController;
import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;


public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // Positive test case: Successfully get all users
    @Test
    public void testGetAllUsers_Success() throws Exception {
        // Create user objects
        Users user1 = new Users();
        user1.setUserId("1");
        user1.setName("User One");
        user1.setEmail("userone@example.com");

        Users user2 = new Users();
        user2.setUserId("2");
        user2.setName("User Two");
        user2.setEmail("usertwo@example.com");

       
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        
      
  

        mockMvc.perform(get("/api/user/get/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"userId\":\"1\",\"name\":\"User One\",\"email\":\"userone@example.com\"}," +
                        "{\"userId\":\"2\",\"name\":\"User Two\",\"email\":\"usertwo@example.com\"}]"));
    }

    // Negative test case: User not found when getting by ID
    // @Test
    // public void testGetUserById_NotFound() throws Exception {
    //     when(userService.findUserByID("1")).thenThrow(new UserException("User not found"));

    //     mockMvc.perform(get("/api/user/get/{id}", "1"))
    //             .andExpect(status().isNotFound())
    //             .andExpect(content().string("User not found"));
    // }

    // Positive test case: Successfully get user profile
    @Test
    public void testGetUserProfile_Success() throws Exception {
        String jwt = "some_jwt_token";
        Users user = new Users();
        user.setUserId("1");
        user.setName("User One");
        user.setEmail("userone@example.com");

        when(userService.findByJwtToken(jwt)).thenReturn(user);
       
        
        mockMvc.perform(get("/api/user/profile").header("Authorization", jwt))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"userId\":\"1\",\"name\":\"User One\",\"email\":\"userone@example.com\"}"));
    }

    // Negative test case: JWT token not valid when getting user profile
    // @Test
    // public void testGetUserProfile_InvalidToken() throws Exception {
    //     String jwt = "invalid_jwt";

    //     when(userService.findByJwtToken(jwt)).thenThrow(new UserException("Invalid token"));

    //     mockMvc.perform(get("/api/user/profile").header("Authorization", jwt))
    //             .andExpect(status().isForbidden())
    //             .andExpect(content().string("Invalid token"));
    // }

    // Positive test case: Successfully update user profile
    @Test
    public void testUpdateUser_Success() throws Exception {
        String jwt = "some_jwt_token";
        Users userData = new Users();
        userData.setUserId("1");
        userData.setName("Updated User");
        userData.setEmail("updated@example.com");

        Users updatedUser = new Users();
        updatedUser.setUserId("1");
        updatedUser.setName("Updated User");
        updatedUser.setEmail("updated@example.com");

        when(userService.updateUser(any(Users.class), eq(jwt))).thenReturn(updatedUser);
        
        mockMvc.perform(post("/api/user/profile/edit")
                .header("Authorization", jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"1\",\"name\":\"Updated User\",\"email\":\"updated@example.com\"}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"userId\":\"1\",\"name\":\"Updated User\",\"email\":\"updated@example.com\"}"));
    }

    // Negative test case: Error occurred when updating user profile
    // @Test
    // public void testUpdateUser_Error() throws Exception {
    //     String jwt = "some_jwt_token";
    //     Users userData = new Users();
    //     userData.setUserId("1");
    
    //     when(userService.updateUser(any(Users.class), eq(jwt))).thenThrow(new UserException("Update failed"));
    
    //     mockMvc.perform(post("/api/user/profile/edit")
    //             .header("Authorization", jwt)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("{\"userId\":\"1\"}"))
    //             .andExpect(status().isForbidden())
    //             .andExpect(content().json("{\"message\":\"Update failed\",\"status\":false}")); // Adjust this if your response format is JSON
    // }
    

    // Positive test case: Successfully delete user
    @Test
    public void testDeleteUser_Success() throws Exception {
        String jwt = "some_jwt_token";

        mockMvc.perform(delete("/api/user/profile/delete/{userId}", "1")
                .header("Authorization", jwt))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Successfully Deleted!\",\"status\":true}"));
    }

    // Negative test case: User not found when deleting user
    @Test
    public void testDeleteUser_NotFound() throws Exception {
        String jwt = "some_jwt_token";

        doThrow(new UserException("User not found")).when(userService).deleteUser(any(String.class), eq(jwt));

        mockMvc.perform(delete("/api/user/profile/delete/{userId}", "1")
                .header("Authorization", jwt))
                .andExpect(status().isForbidden())
                .andExpect(content().json("{\"message\":\"User not found\",\"status\":false}"));
               
    }
}
