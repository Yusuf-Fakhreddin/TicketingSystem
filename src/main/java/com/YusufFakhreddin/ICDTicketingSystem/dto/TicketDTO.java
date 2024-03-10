package com.YusufFakhreddin.ICDTicketingSystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicketDTO {

    @NotNull
    private String id;
    @Size(min = 5,max=100, message = "title must be between 5 and 100 characters")
    private String title;
    @Size(min = 5,max=1000, message = "description must be between 5 and 1000 characters")
    private String description;
    private String status;
    private String priority;
    private String type;
    private String date;
    private String time;
    private String owner;
    private String ownerTeam;
    private String assigned_user;
    private String assigned_team;
    private String comments;


}
