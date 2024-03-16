package com.YusufFakhreddin.ICDTicketingSystem.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    private String comment;
    private String date;
    private String time;
    private String author_username;
}
