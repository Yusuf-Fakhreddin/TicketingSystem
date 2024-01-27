package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    Ticket getTicket(String id);
    List<Ticket> getAllTickets();
    Ticket updateTicket(String id,Ticket ticket);
    void deleteTicket(String id);
}

