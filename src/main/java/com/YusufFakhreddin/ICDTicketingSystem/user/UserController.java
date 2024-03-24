package com.YusufFakhreddin.ICDTicketingSystem.user;

import com.YusufFakhreddin.ICDTicketingSystem.errorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.mapper.ModelMapperUtil;

import com.YusufFakhreddin.ICDTicketingSystem.team.enums.TeamName;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.enums.TicketStatus;
import com.YusufFakhreddin.ICDTicketingSystem.response.ApiResopnse;
import com.YusufFakhreddin.ICDTicketingSystem.team.TeamService;
import com.YusufFakhreddin.ICDTicketingSystem.utilities.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BeanUtil beanUtil;

    @PostMapping
    public ApiResopnse<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ApiResopnse<>(HttpStatus.CREATED.value(), "User created successfully", userService.createUser(userDTO));
    }

    @GetMapping("/{username}")
    public ApiResopnse<UserDTO> getUser(@PathVariable String username) {
        return new ApiResopnse<>(HttpStatus.OK.value(), "User retrieved successfully", userService.getUser(username));
    }

    @DeleteMapping("/{username}")
    public ApiResopnse<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ApiResopnse<>(HttpStatus.OK.value(), "User deleted successfully", null);
    }

    @PutMapping("/{username}")
    public ApiResopnse<UserDTO> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        System.out.println("UserDTO: " + userDTO);
        // Get the user from the database
        UserDTO existingUser = userService.getUser(username);

//        if the user is not found, return a 404 response
        if (existingUser == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "User not found");
        }

//        if the username is not the same as the one in the request body, return a custom exception username cannot be changed
        if (userDTO.getUsername()!=null && !existingUser.getUsername().equals(userDTO.getUsername())) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value() ,"Username cannot be changed");
        }

        // Check if the password field in the UserDTO is null
        if (userDTO.getPassword() == null) {
            // If it's null, set the password of the UserDTO to the existing user's password
            userDTO.setPassword(existingUser.getPassword());
        }

        // Copy the properties of the UserDTO to the existing user
        BeanUtils.copyProperties(userDTO, existingUser, beanUtil.getNullPropertyNames(userDTO));

        // Update the user in the database
        return new ApiResopnse<>(HttpStatus.OK.value(), "User updated successfully", userService.updateUser(username, existingUser));
    }


    @GetMapping
    public ApiResopnse<Page<UserDTO>> getAllUsers(@RequestParam(required = false) TicketStatus status, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Users retrieved successfully", userService.getAllUsers(pageRequest));
    }

    @GetMapping("/search/{query}")
    public ApiResopnse<Page<UserDTO>> searchUsers(@PathVariable String query,@RequestParam(required = false) TicketStatus status, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Users retrieved successfully", userService.searchUsers(query, pageRequest));
    }

    @GetMapping("/team/{teamName}")
    public ApiResopnse<List<UserDTO>> getUsersByTeam(@PathVariable TeamName teamName) {
        return new ApiResopnse<>(HttpStatus.OK.value(), "Users retrieved successfully", userService.getUsersByTeam(teamName));
    }
}
