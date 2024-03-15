package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketResolutionDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TeamName;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;

import java.util.List;

public interface TicketService {
    TicketDTO createTicket(Ticket ticket);
    TicketDTO getTicket(String id);
    List<TicketDTO> getAllTickets();
    TicketDTO updateTicket(String id,TicketDTO ticketDTO);
    void deleteTicket(String id);

    TicketDTO addCommentToTicket(String id, Comment comment);

    List<TicketDTO> getTicketsByOwner(String username);

    List<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, TicketStatus status);

    //   resolve ticket by id and resolution string

    TicketDTO resolveTicket(String id, TicketResolutionDTO TicketResolution);
}

