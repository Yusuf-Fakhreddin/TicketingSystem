package com.YusufFakhreddin.ICDTicketingSystem.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private String username;
    private boolean active;
    @JsonIgnore
    private String password;
}
