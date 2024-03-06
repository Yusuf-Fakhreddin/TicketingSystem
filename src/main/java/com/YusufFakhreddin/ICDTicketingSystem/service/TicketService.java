package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    Ticket getTicket(String id);
    List<Ticket> getAllTickets();
    Ticket updateTicket(String id,Ticket ticket);
    void deleteTicket(String id);

    Ticket addCommentToTicket(String id, Comment comment);

    List<Ticket> getTicketsByOwner(String username);

    List<Ticket> findTicketsByOwnerAndStatusWithoutComments(String username, String status);

    List<Ticket> findTicketsByTeamAndStatusWithoutComments(String teamName, String status);

    List<Ticket>  getTicketsByTeamWithoutComments(String team);
}

