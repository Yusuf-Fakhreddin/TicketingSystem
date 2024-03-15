package com.YusufFakhreddin.ICDTicketingSystem.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    private String ticket_id;
    private String comment;
    private String author_username;
}
