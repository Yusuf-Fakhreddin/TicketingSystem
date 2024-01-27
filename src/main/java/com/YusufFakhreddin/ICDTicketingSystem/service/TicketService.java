package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    Ticket getTicket(String id);
    List<Ticket> getAllTickets();
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(String id);
}

