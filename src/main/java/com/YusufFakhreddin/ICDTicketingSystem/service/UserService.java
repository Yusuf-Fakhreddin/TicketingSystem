package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dao.UserRepo;
import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User findUserByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.orElse(null);
    }

    public User findUserById(String id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }
}
