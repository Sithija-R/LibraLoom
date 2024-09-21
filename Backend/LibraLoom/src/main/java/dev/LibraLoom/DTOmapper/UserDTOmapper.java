package dev.LibraLoom.DTOmapper;

import dev.LibraLoom.DTO.UserDTO;
import dev.LibraLoom.Models.Users;

public class UserDTOmapper {
    
public static UserDTO mapToUserDTO(Users user){
    UserDTO userDTO = new UserDTO();

    userDTO.setUserId(user.getUserId());
    userDTO.setEmail(user.getEmail());
    userDTO.setName(user.getName());
    userDTO.setBorroweBooks(user.getBorroweBooks());
    userDTO.setReservedBook(user.getReservedBook());

    return userDTO;

}



}
