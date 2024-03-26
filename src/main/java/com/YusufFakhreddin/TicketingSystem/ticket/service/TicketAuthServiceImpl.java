package com.YusufFakhreddin.TicketingSystem.ticket.service;

import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TicketAuthServiceImpl implements TicketAuthService {
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
}
