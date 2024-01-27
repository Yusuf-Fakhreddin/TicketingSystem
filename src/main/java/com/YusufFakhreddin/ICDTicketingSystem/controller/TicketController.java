package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.service.TicketService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Ticket> getTicket(@PathVariable String id) throws CustomException {
//        log the id
        System.out.println(id);
        Ticket ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        //        log executing this method and the ticket
        System.out.println("Creating ticket");
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        ticket.setId("0");
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String id, @RequestBody Ticket ticket) throws CustomException {
        //        log executing this method and the ticket
        System.out.println("Updating ticket");

        ticketService.updateTicket(id,ticket);
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) throws CustomException {
        //        log executing this method
        System.out.println("Deleting ticket");

        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }



}