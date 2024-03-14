package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.dto.ModelMapperUtil;
import com.YusufFakhreddin.ICDTicketingSystem.dto.UserDTO;

import com.YusufFakhreddin.ICDTicketingSystem.response.ApiResopnse;
import com.YusufFakhreddin.ICDTicketingSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
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
        return new ApiResopnse<>(HttpStatus.OK.value(), "User updated successfully", userService.updateUser(username, userDTO));
    }


    @GetMapping
    public ApiResopnse<List<UserDTO>> getAllUsers() {
        return new ApiResopnse<>(HttpStatus.OK.value(), "Users retrieved successfully", userService.getAllUsers());
    }

    @GetMapping("/search/{query}")
    public ApiResopnse<List<UserDTO>> searchUsers(@PathVariable String query) {
        return new ApiResopnse<>(HttpStatus.OK.value(), "Users retrieved successfully", userService.searchUsers(query));
    }
}
