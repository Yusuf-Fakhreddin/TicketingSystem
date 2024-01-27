package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
//        log executing this method
        System.out.println("Getting all tickets");
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable String id) {
//        log the id
        System.out.println(id);
        return ResponseEntity.ok(ticketService.getTicket(id));
    }



}