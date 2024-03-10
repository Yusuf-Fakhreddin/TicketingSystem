package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import java.util.List;

public interface TicketService {
    TicketDTO createTicket(Ticket ticket);
    TicketDTO getTicket(String id);
    List<TicketDTO> getAllTickets();
    TicketDTO updateTicket(String id,TicketDTO ticketDTO);
    void deleteTicket(String id);

    TicketDTO addCommentToTicket(String id, Comment comment);

    List<TicketDTO> getTicketsByOwner(String username);

    List<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, String status);

    List<TicketDTO> findTicketsByTeamAndStatusWithoutComments(String teamName, String status);

    List<TicketDTO>  getTicketsByTeamWithoutComments(String team);
}

