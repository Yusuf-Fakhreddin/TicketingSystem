package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.response.ApiResopnse;
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
    public ApiResopnse<List<Ticket>> getAllTickets() {
//        log executing this method
        System.out.println("Getting all tickets");
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ApiResopnse<Ticket> getTicket(@PathVariable String id) throws CustomException {
//        log the id
        System.out.println(id);
        Ticket ticket = ticketService.getTicket(id);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket retrieved successfully", ticket);
    }

    @PostMapping
    public ApiResopnse<?> createTicket(@RequestBody @Validated Ticket ticket, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }
        System.out.println("Creating ticket");
        ticket.setId("0");
        Ticket createdTicket = ticketService.createTicket(ticket);
        return new ApiResopnse<>(HttpStatus.CREATED.value(), "Ticket created successfully", createdTicket);
    }
    @PutMapping("/{id}")
    public ApiResopnse<?> updateTicket(@PathVariable String id, @RequestBody @Validated Ticket ticket, BindingResult bindingResult) throws CustomException {
        System.out.println("Updating ticket");

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }

        ticketService.updateTicket(id, ticket);
//        find ticket updated by id
        Ticket updatedTicket = ticketService.getTicket(id);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket updated successfully", updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ApiResopnse<Void> deleteTicket(@PathVariable String id) throws CustomException {
        //        log executing this method
        System.out.println("Deleting ticket");

        ticketService.deleteTicket(id);
        return new ApiResopnse<>(HttpStatus.NO_CONTENT.value(), "Ticket deleted successfully", null);
    }



}