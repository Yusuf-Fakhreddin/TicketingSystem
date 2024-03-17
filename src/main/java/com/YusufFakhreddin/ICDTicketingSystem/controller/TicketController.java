package com.YusufFakhreddin.ICDTicketingSystem.controller;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.dto.*;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.entity.User;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import com.YusufFakhreddin.ICDTicketingSystem.response.ApiResopnse;
import com.YusufFakhreddin.ICDTicketingSystem.service.TicketService;
import com.YusufFakhreddin.ICDTicketingSystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
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
//    With parameters: /all?page=1&size=20
//    Without parameters: /all (this will use the default values)
    public ApiResopnse<Page<TicketDTO>> getAllTickets(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "10") Integer size) {
        // create page request
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TicketDTO> tickets = ticketService.getAllTickets(pageRequest);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", tickets );
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
        TicketDTO createdTicket = ticketService.createTicket(ticket);
        return new ApiResopnse<>(HttpStatus.CREATED.value(), "Ticket created successfully", createdTicket);
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
    public ApiResopnse<?> addCommentToTicket(@PathVariable String id, @RequestBody CommentDTO comment,Principal principal) {
        System.out.println("Adding comment to ticket");
//        Create a new comment object to set the date and time
        User loggedInUser = userService.findUserByUsername(principal.getName());
        Comment newComment = new Comment(id, comment.getComment(), loggedInUser);
        TicketDTO ticket= ticketService.addCommentToTicket(id, newComment);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Comment added successfully", ticket);
    }

    @GetMapping("/owner/{username}")
    public ApiResopnse<Page<TicketDTO>> getTicketsByOwner(@PathVariable String username, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getTicketsByOwner(username, pageRequest));
    }

    @GetMapping("/owner/{username}/status/{status}")
    public ApiResopnse<Page<TicketDTO>> getTicketsByOwnerAndStatus(@PathVariable String username, @PathVariable TicketStatus status,@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                   @RequestParam(required = false, defaultValue = "10") Integer size ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.findTicketsByOwnerAndStatusWithoutComments(username, status, pageRequest));
    }


@GetMapping("/my-tickets")
    public ApiResopnse<Page<TicketDTO>> getMyTickets(@RequestParam(required = false) TicketStatus status, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(required = false, defaultValue = "10") Integer size , Principal principal) {
        PageRequest pageRequest = PageRequest.of(page, size);
        User user = userService.findUserByUsername(principal.getName());
        if (status == null) {
            return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.getTicketsByOwner(user.getUsername(),pageRequest));
        }
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", ticketService.findTicketsByOwnerAndStatusWithoutComments(user.getUsername(), status,pageRequest));
    }


    @PutMapping("/resolve/{id}")
    public ApiResopnse<TicketDTO> resolveTicket(@PathVariable String id, @RequestBody TicketResolutionDTO TicketResolution) {
        String resolution = TicketResolution.getResolution();
        TicketStatus status = TicketResolution.getStatus();
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket resolved successfully", ticketService.resolveTicket(id, TicketResolution));
    }


}