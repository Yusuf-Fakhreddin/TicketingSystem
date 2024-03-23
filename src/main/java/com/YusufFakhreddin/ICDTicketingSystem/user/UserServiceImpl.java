package com.YusufFakhreddin.ICDTicketingSystem.user;

import com.YusufFakhreddin.ICDTicketingSystem.errorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.team.TeamRepo;
import com.YusufFakhreddin.ICDTicketingSystem.utilities.ModelMapperUtil;
import com.YusufFakhreddin.ICDTicketingSystem.team.Team;
import com.YusufFakhreddin.ICDTicketingSystem.team.enums.TeamName;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final TeamRepo teamRepo;
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
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepo.findAll(pageable);
        return users.map(user -> modelMapperUtil.mapObject(user, UserDTO.class));
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
    public Page<UserDTO> searchUsers(String query,Pageable pageable) {
        Page<User> users = userRepo.findByUsernameContaining(query,pageable);
        return users.map(user -> modelMapperUtil.mapObject(user, UserDTO.class));
    }

    public List<UserDTO> getUsersByTeam(TeamName teamName) {
        Team team = teamRepo.findByName(teamName);
        if (team == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Team not found - " + teamName);
        }
        Set<User> users = team.getUsers();
        return team.getUsers().stream()
                .map(user -> modelMapperUtil.mapObject(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
