package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dao.CommentRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dao.TicketRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dto.ModelMapperUtil;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketResolutionDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    public TicketDTO createTicket(Ticket ticket){
        Ticket newTicket = ticketRepo.save(ticket);
        return modelMapperUtil.mapObject(newTicket, TicketDTO.class);
    }

    @Override
    public TicketDTO getTicket(String id){
        // check for id existence and throw custom error if not
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        return modelMapperUtil.mapObject(ticket, TicketDTO.class);

    }

    @Override
    public Page<TicketDTO> getAllTickets(Pageable pageable){
        Page<Ticket> tickets = ticketRepo.findAll(pageable);
        return tickets.map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class));

    }

    @Override
    public TicketDTO updateTicket(String id , TicketDTO ticketDTO){
        // check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        // Convert TicketDTO to Ticket entity
        Ticket ticket = modelMapperUtil.mapObject(ticketDTO, Ticket.class);
        // append id to ticket object
        ticket.setId(id);
        Ticket updatedTicket = ticketRepo.save(ticket);
        // Convert updated Ticket back to TicketDTO
        TicketDTO updatedTicketDTO = modelMapperUtil.mapObject(updatedTicket, TicketDTO.class);
        return updatedTicketDTO;
    }

    @Override
    public void deleteTicket(String id){

// check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        ticketRepo.deleteById(id);
    }


    public TicketDTO addCommentToTicket(String ticketId, Comment comment) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + ticketId));
        ticket.addComment(comment);
        commentRepo.save(comment);
        ticketRepo.save(ticket);
        return modelMapperUtil.mapObject(ticket, TicketDTO.class);
    }

    public Page<TicketDTO> getTicketsByOwner(String username,Pageable pageable){
        Page<Ticket> tickets= ticketRepo.findTicketsByOwner_Username(username, pageable);
        return tickets.map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class));

    }

    @Override
    public Page<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, TicketStatus status, Pageable pageable) {
        Page<Ticket> tickets=  ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status, pageable);
        return tickets.map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class));
    }


    public List<TicketDTO> getTicketsByOwnerAndStatus(String username, TicketStatus status, Pageable pageable){
        Page<Ticket> tickets= ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status, pageable);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public TicketDTO resolveTicket(String id, TicketResolutionDTO TicketResolution) {
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        ticket.setStatus(TicketResolution.getStatus());
        ticket.setResolution(TicketResolution.getResolution());
        Ticket updatedTicket = ticketRepo.save(ticket);
        return modelMapperUtil.mapObject(updatedTicket, TicketDTO.class);
    }


}
