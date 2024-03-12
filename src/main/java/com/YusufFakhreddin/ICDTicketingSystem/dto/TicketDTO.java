package com.YusufFakhreddin.ICDTicketingSystem.dto;

import com.YusufFakhreddin.ICDTicketingSystem.enums.TeamName;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketPriority;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketType;
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
    private TicketStatus status;
    private TicketPriority priority;
    private TicketType type;
    private String date;
    private String time;
    private String owner;
    private TeamName ownerTeam;
    private String assigned_user;
    private String assigned_team;
    private String comments;


}
