package com.YusufFakhreddin.TicketingSystem.ticket.service;


import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;

public interface TicketAuthService {
    boolean isOwnerOrAssignedUser(TicketDTO ticketDTO);
    boolean isAssignedUser(TicketDTO ticketDTO);
}
