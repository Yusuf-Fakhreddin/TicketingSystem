package com.YusufFakhreddin.TicketingSystem.ticket.service;


import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;
import org.springframework.stereotype.Service;

@Service
public interface TicketAuthService {
    boolean isOwnerOrAssignedUser(TicketDTO ticketDTO);
    boolean isAssignedUser(TicketDTO ticketDTO);

    boolean isAssignedTeamMember(TicketDTO ticketDTO);
}
