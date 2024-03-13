package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dao.UserRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dto.UserDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUser(String id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);

    User findUserByUsername(String username);
    User findUserById(String id);

    List<UserDTO> searchUsers(String query);
}
