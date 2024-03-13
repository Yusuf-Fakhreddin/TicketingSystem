package com.YusufFakhreddin.ICDTicketingSystem.dao;

import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    List<User> findByUsernameContaining(String query);
}