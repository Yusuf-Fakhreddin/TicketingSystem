package com.YusufFakhreddin.ICDTicketingSystem.dao;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, String> {

}
