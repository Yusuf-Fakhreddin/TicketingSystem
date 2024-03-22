package com.YusufFakhreddin.ICDTicketingSystem.dto;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TeamName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private String username;
    private boolean active;
    @JsonIgnore
    private String password;
}
