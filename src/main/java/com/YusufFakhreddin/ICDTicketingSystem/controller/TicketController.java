package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Ticket> createTicket(@RequestBody @Validated Ticket ticket, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }
        System.out.println("Creating ticket");
        ticket.setId("0");
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable String id, @RequestBody @Validated Ticket ticket, BindingResult bindingResult) throws CustomException {
        System.out.println("Updating ticket");

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }

        ticketService.updateTicket(id, ticket);
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