package com.YusufFakhreddin.ICDTicketingSystem.dto;

import com.YusufFakhreddin.ICDTicketingSystem.enums.TeamName;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketPriority;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class TicketDTO {

    private String id;
    @Size(min = 5,max=100, message = "title must be between 5 and 100 characters")
    private String title;
    @Size(min = 5,max=1000, message = "description must be between 5 and 1000 characters")
    private String description;
    private String resolution;
    private TicketStatus status= TicketStatus.QUEUED;
    private TicketPriority priority;
    private TicketType type;
    private LocalDate date=LocalDate.now();
    private LocalTime time=LocalTime.now();
    private String owner;
    private TeamName ownerTeam;
    private String assignedUser;
    private TeamName assignedTeam;
    private List<CommentDTO> comments;

    private List<AttachmentDTO> attachments;
}
