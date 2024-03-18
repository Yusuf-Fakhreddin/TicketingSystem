package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dto.UserDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUser(String id);
    Page<UserDTO> getAllUsers(Pageable pageable);
    UserDTO updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);

    User findUserByUsername(String username);

    Page<UserDTO> searchUsers(String query,Pageable pageable);
}
