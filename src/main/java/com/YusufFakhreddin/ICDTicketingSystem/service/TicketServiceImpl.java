package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.dao.TicketRepo;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
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
        return ticketRepo.findById(id).get();
    }

    @Override
    public List<Ticket> getAllTickets(){
        return ticketRepo.findAll();
    }

    @Override
    public Ticket updateTicket(Ticket ticket){return ticketRepo.save(ticket);}

    @Override
    public void deleteTicket(String id){
        ticketRepo.deleteById(id);
    }
}
