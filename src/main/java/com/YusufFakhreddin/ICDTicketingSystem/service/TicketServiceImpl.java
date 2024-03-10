package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dao.CommentRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dao.TicketRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dto.ModelMapperUtil;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public List<TicketDTO> getAllTickets(){
        List<Ticket> tickets = ticketRepo.findAll();
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public TicketDTO updateTicket(String id , Ticket ticket){
        // check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        // append id to ticket object
        ticket.setId(id);
        Ticket updatedTicket = ticketRepo.save(ticket);
        return modelMapperUtil.mapObject(updatedTicket, TicketDTO.class);
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

    public List<TicketDTO> getTicketsByOwner(String username){
        List<Ticket> tickets= ticketRepo.findTicketsByOwner_Username(username);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, String status) {
        List<Ticket> tickets=  ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> findTicketsByTeamAndStatusWithoutComments(String teamName, String status) {
        List<Ticket> tickets=  ticketRepo.findTicketsByTeamAndStatusWithoutComments(teamName, status);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<TicketDTO> getTicketsByTeamWithoutComments(String team) {
        List<Ticket> tickets=  ticketRepo.findTicketsByTeamWithoutComments(team);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }

    public List<TicketDTO> getTicketsByOwnerAndStatus(String username, String status){
        List<Ticket> tickets= ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }


    public List<TicketDTO> getTicketsByTeamAndStatus(String teamName, String status){
        List<Ticket> tickets= ticketRepo.findTicketsByTeamAndStatusWithoutComments(teamName, status);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
                .collect(Collectors.toList());
    }


}
