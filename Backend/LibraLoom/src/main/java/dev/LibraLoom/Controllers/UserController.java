package dev.LibraLoom.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.LibraLoom.DTO.UserDTO;
import dev.LibraLoom.DTOmapper.UserDTOmapper;
import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Response.ApiResponse;
import dev.LibraLoom.Services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    // get all
    @GetMapping("/get/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<Users> users = userService.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (Users user : users) {
            UserDTO userDTO = UserDTOmapper.mapToUserDTO(user);
            userDTOs.add(userDTO);
        }
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        Users user = userService.findUserByID(id);
        UserDTO userDTO = UserDTOmapper.mapToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // get profile
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) {
        Users reqUser = userService.findByJwtToken(jwt);

        UserDTO userDTO = UserDTOmapper.mapToUserDTO(reqUser);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.ACCEPTED);
    }

    // edit profile
    @PostMapping("profile/edit")
    public ResponseEntity<UserDTO> updateUser(@RequestBody Users userData, @RequestHeader("Authorization") String jwt) {

        Users updatedUser = userService.updateUser(userData, jwt);
        UserDTO updatedUserDTO = UserDTOmapper.mapToUserDTO(updatedUser);

        return new ResponseEntity<UserDTO>(updatedUserDTO, HttpStatus.ACCEPTED);
    }

    //delete user
    @DeleteMapping("/profile/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId, @RequestHeader("Authorization") String jwt) {

        try {
            userService.deleteUser(userId, jwt);
            
            ApiResponse response = new ApiResponse("Successfully Deleted!", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (UserException e) {
            
            ApiResponse response = new ApiResponse(e.getMessage(), false);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            
        } catch (RuntimeException e) {

            ApiResponse response = new ApiResponse("Error occurred: " + e.getMessage(), false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
