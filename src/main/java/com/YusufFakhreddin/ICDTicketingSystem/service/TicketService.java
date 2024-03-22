package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dto.AttachmentDTO;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketResolutionDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TicketService {
    TicketDTO createTicket(Ticket ticket);
    TicketDTO getTicket(String id);
    Page<TicketDTO> getAllTickets(Pageable pageable);
    TicketDTO updateTicket(String id,TicketDTO ticketDTO);
    void deleteTicket(String id);

    TicketDTO addCommentToTicket(String id, Comment comment);

    Page<TicketDTO> getTicketsByOwner(String username,Pageable pageable);

    Page<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, TicketStatus status, Pageable pageable);

    //   resolve ticket by id and resolution string

    TicketDTO resolveTicket(String id, TicketResolutionDTO TicketResolution);

    TicketDTO save(TicketDTO ticket);

    AttachmentDTO getAttachment(String id);

    TicketDTO addFileToTicket(String ticketId, MultipartFile file) throws IOException;
}

