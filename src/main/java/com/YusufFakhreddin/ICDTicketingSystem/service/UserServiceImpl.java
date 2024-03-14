package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dao.UserRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dto.ModelMapperUtil;
import com.YusufFakhreddin.ICDTicketingSystem.dto.UserDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = modelMapperUtil.mapObject(userDTO, User.class);
//        TODO: encrypt the password before saving using bcrypt
              User newUser = userRepo.save(user);
              return modelMapperUtil.mapObject(newUser, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String username) {
//        check for id existence and throw custom error if not
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Username not found - " + username);
        }
        return modelMapperUtil.mapObject(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> modelMapperUtil.mapObject(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO updateUser(String username, UserDTO userDTO) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"User username not found - " + username);
        }
        User updatedUser = modelMapperUtil.mapObject(userDTO, User.class);
        updatedUser.setUsername(username);
        User newUser = userRepo.save(updatedUser);
        return modelMapperUtil.mapObject(newUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"User username not found - " + username);
        }
        userRepo.deleteByUsername(username);
    }

    public User findUserByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.orElse(null);
    }

    public User findUserById(String id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<UserDTO> searchUsers(String query) {
        List<User> users = userRepo.findByUsernameContaining(query);
        return users.stream()
                .map(user -> modelMapperUtil.mapObject(user, UserDTO.class))
                .toList();
    }
}
