package com.YusufFakhreddin.TicketingSystem.comment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    private String comment;
    private LocalDate date=LocalDate.now();
    private LocalTime time=LocalTime.now();
    private String author_username;
}
