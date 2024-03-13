package com.YusufFakhreddin.ICDTicketingSystem.dto;

import com.YusufFakhreddin.ICDTicketingSystem.enums.TeamName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private boolean active;
    private TeamName team;
}
