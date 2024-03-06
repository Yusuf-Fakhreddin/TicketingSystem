package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dao.CommentRepo;
import com.YusufFakhreddin.ICDTicketingSystem.dao.TicketRepo;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private CommentRepo commentRepo;

    public TicketServiceImpl(TicketRepo ticketRepo, CommentRepo commentRepo){
        this.ticketRepo = ticketRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public Ticket createTicket(Ticket ticket){
        return ticketRepo.save(ticket);
    }

    @Override
    public Ticket getTicket(String id){
        // check for id existence and throw custom error if not
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        return ticket;

    }

    @Override
    public List<Ticket> getAllTickets(){
        return ticketRepo.findAll();
    }

    @Override
    public Ticket updateTicket(String id , Ticket ticket){
        // check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        // append id to ticket object
        ticket.setId(id);
        return ticketRepo.save(ticket);
    }

    @Override
    public void deleteTicket(String id){

// check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        ticketRepo.deleteById(id);
    }


    public Ticket addCommentToTicket(String ticketId, Comment comment) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + ticketId));
        ticket.addComment(comment);
        commentRepo.save(comment);
        return ticketRepo.save(ticket);
    }

    public List<Ticket> getTicketsByOwner(String username){
        return ticketRepo.findTicketsByOwner_Username(username);
    }

    @Override
    public List<Ticket> findTicketsByOwnerAndStatusWithoutComments(String username, String status) {
        return ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status);
    }

    @Override
    public List<Ticket> findTicketsByTeamAndStatusWithoutComments(String teamName, String status) {
        return ticketRepo.findTicketsByTeamAndStatusWithoutComments(teamName, status);
    }


    @Override
    public List<Ticket> getTicketsByTeamWithoutComments(String team) {
        return ticketRepo.findTicketsByTeamWithoutComments(team);
    }

    public List<Ticket> getTicketsByOwnerAndStatus(String username, String status){
        return ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status);
    }


    public List<Ticket> getTicketsByTeamAndStatus(String teamName, String status){
        return ticketRepo.findTicketsByTeamAndStatusWithoutComments(teamName, status);
    }


}
