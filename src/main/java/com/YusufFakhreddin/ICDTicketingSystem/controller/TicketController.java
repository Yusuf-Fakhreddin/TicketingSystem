package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dto.ModelMapperUtil;
import com.YusufFakhreddin.ICDTicketingSystem.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import com.YusufFakhreddin.ICDTicketingSystem.response.ApiResopnse;
import com.YusufFakhreddin.ICDTicketingSystem.service.TicketService;
import com.YusufFakhreddin.ICDTicketingSystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @GetMapping
    public ApiResopnse<List<TicketDTO>> getAllTickets() {
//        log executing this method
        System.out.println("Getting all tickets");
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ApiResopnse<TicketDTO> getTicket(@PathVariable String id) throws CustomException {
//        log the id
        System.out.println(id);
//        get ticket and its comments
        TicketDTO ticket = ticketService.getTicket(id);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket retrieved successfully", ticket);
    }

    @PostMapping
    public ApiResopnse<?> createTicket(@RequestBody @Validated TicketDTO ticketDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }
        System.out.println("Creating ticket");

        // Convert TicketDTO to Ticket entity
        Ticket ticket = modelMapperUtil.mapObject(ticketDTO, Ticket.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());



        ticket.setOwner(user);
        ticket.setOwnerTeam(user.getTeam());
        TicketDTO createdTicket = ticketService.createTicket(ticket);
        return new ApiResopnse<>(HttpStatus.CREATED.value(), "Ticket created successfully", createdTicket);
    }

    @PutMapping("/ownership/{id}")
    public ApiResopnse<?> updateTicketOwner(@PathVariable String id, @RequestBody @Validated User newOwner, BindingResult bindingResult) throws CustomException {
        System.out.println("Updating ticket owner");

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }

        // Fetch the existing ticketDTO
        TicketDTO existingTicketDTO = ticketService.getTicket(id);

        // Convert TicketDTO to Ticket entity
        Ticket existingTicket = modelMapperUtil.mapObject(existingTicketDTO, Ticket.class);

        // Update the owner property
        existingTicket.setOwner(newOwner);

        // Convert updated Ticket back to TicketDTO
        TicketDTO updatedTicketDTO = modelMapperUtil.mapObject(existingTicket, TicketDTO.class);

        // Save the updated ticketDTO
        ticketService.updateTicket(id, updatedTicketDTO);

        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket owner updated successfully", updatedTicketDTO);
    }


    @PutMapping("/status/{id}")
    public ApiResopnse<?> updateTicketStatus(@PathVariable String id, @RequestBody String newStatus, BindingResult bindingResult) throws CustomException {
        System.out.println("Updating ticket status");

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors.toString());
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }

        // Fetch the existing ticketDTO
        TicketDTO existingTicketDTO = ticketService.getTicket(id);

        // Convert TicketDTO to Ticket entity
        Ticket existingTicket = modelMapperUtil.mapObject(existingTicketDTO, Ticket.class);

        // Update the status property
        existingTicket.setStatus(newStatus);

        // Convert updated Ticket back to TicketDTO
        TicketDTO updatedTicketDTO = modelMapperUtil.mapObject(existingTicket, TicketDTO.class);

        // Save the updated ticketDTO
        ticketService.updateTicket(id, updatedTicketDTO);

        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket status updated successfully", updatedTicketDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResopnse<Void> deleteTicket(@PathVariable String id) throws CustomException {
        //        log executing this method
        System.out.println("Deleting ticket");

        ticketService.deleteTicket(id);
        return new ApiResopnse<>(HttpStatus.NO_CONTENT.value(), "Ticket deleted successfully", null);
    }


    @PostMapping("/{id}/comments")
    @Transactional
    public ApiResopnse<?> addCommentToTicket(@PathVariable String id, @RequestBody Comment comment) {
        TicketDTO ticket= ticketService.addCommentToTicket(id, comment);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Comment added successfully", ticket);
    }

    @GetMapping("/owner/{username}")
    public ApiResopnse<List<TicketDTO>> getTicketsByOwner(@PathVariable String username) {
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getTicketsByOwner(username));
    }

    @GetMapping("/owner/{username}/status/{status}")
    public ApiResopnse<List<TicketDTO>> getTicketsByOwnerAndStatus(@PathVariable String username, @PathVariable String status) {
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.findTicketsByOwnerAndStatusWithoutComments(username, status));
    }

//    endpoint for logged in user to get their tickets with optional parameter status in the url
    @GetMapping("/my-tickets")
    public ApiResopnse<List<TicketDTO>> getMyTickets(@RequestParam(required = false) String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        if (status == null) {
            return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getTicketsByOwner(user.getUsername()));
        }
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.findTicketsByOwnerAndStatusWithoutComments(user.getUsername(), status));
    }

//   get logged in user's team tickets
    @GetMapping("/my-team-tickets")
    public ApiResopnse<List<TicketDTO>> getMyTeamTickets(@RequestParam(required = false) String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        if (status == null) {
            return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getTicketsByTeamWithoutComments(user.getTeam().getName()));
        }
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.findTicketsByTeamAndStatusWithoutComments(user.getTeam().getName(), status));
    }



}