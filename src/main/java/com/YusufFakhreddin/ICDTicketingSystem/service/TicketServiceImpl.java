package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dao.TicketRepo;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    private TicketRepo ticketRepo;

    public TicketServiceImpl(TicketRepo ticketRepo){
        this.ticketRepo = ticketRepo;
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
}
