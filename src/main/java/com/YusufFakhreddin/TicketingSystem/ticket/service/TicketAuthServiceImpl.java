package com.YusufFakhreddin.TicketingSystem.ticket.service;

import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;
import com.YusufFakhreddin.TicketingSystem.user.UserDTO;
import com.YusufFakhreddin.TicketingSystem.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketAuthServiceImpl implements TicketAuthService {

    private final UserService userService;

    public boolean isOwnerOrAssignedUser(TicketDTO ticketDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ticketDTO.getOwner().equals(username) || ticketDTO.getAssignedUser().equals(username);
    }

    public boolean isAssignedUser(TicketDTO ticketDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ticketDTO.getAssignedUser().equals(username);
    }

        public boolean isAssignedTeamMember(TicketDTO ticketDTO) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            List<UserDTO> teamMembers = userService.getUsersByTeam(ticketDTO.getAssignedTeam());

            return teamMembers.stream().anyMatch(user -> user.getUsername().equals(username));
        }



}
