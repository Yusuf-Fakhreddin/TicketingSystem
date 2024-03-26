package com.YusufFakhreddin.TicketingSystem.comment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String author_username;
}
