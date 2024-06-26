package com.YusufFakhreddin.TicketingSystem.ticket;

import com.YusufFakhreddin.TicketingSystem.errorHandling.CustomException;
import com.YusufFakhreddin.TicketingSystem.attachment.AttachmentDTO;
import com.YusufFakhreddin.TicketingSystem.comment.CommentDTO;
import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;
import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketResolutionDTO;
import com.YusufFakhreddin.TicketingSystem.ticket.enums.TicketStatus;
import com.YusufFakhreddin.TicketingSystem.response.ApiResopnse;
import com.YusufFakhreddin.TicketingSystem.ticket.service.TicketAuthService;
import com.YusufFakhreddin.TicketingSystem.ticket.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketAuthService ticketAuthService;
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
        TicketDTO ticket = ticketService.getTicket(id);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket retrieved successfully", ticket);
    }

    @PostMapping
    public ApiResopnse<?> createTicket(@RequestBody @Validated TicketDTO ticketDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation errors occurred", errors);
        }

        // Get the authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ticketDTO.setOwner(auth.getName());

        TicketDTO createdTicket = ticketService.createTicket(ticketDTO);
        return new ApiResopnse<>(HttpStatus.CREATED.value(), "Ticket created successfully", createdTicket);
    }

    @PutMapping("/{id}/{username}")
//    update the ticket assigned user to be the logged in user
    public ApiResopnse<TicketDTO> assignTicketToUser(@PathVariable String id,@PathVariable String username) {
    // only the assignedTeam members can assign the ticket to the username in the path
        TicketDTO ticketDTO = ticketService.getTicket(id);
        if (!ticketAuthService.isAssignedTeamMember(ticketDTO)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to assign this ticket", null);
        }

//        find ticket by id
        TicketDTO ticket = ticketService.getTicket(id);
//        assign the ticket to the logged in user
        ticket.setAssignedUser(username);
        TicketDTO updatedTicket = ticketService.updateTicket(id,ticket);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket assigned successfully", updatedTicket);
    }


    @DeleteMapping("/{id}")
    public ApiResopnse<Void> deleteTicket(@PathVariable String id) throws CustomException {
        // only assigned team members can delete the ticket
        TicketDTO ticketDTO = ticketService.getTicket(id);
        if (!ticketAuthService.isAssignedTeamMember(ticketDTO)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to delete this ticket", null);
        }
        ticketService.deleteTicket(id);
        return new ApiResopnse<>(HttpStatus.NO_CONTENT.value(), "Ticket deleted successfully", null);
    }


    @PostMapping("/{id}/comments")
    @Transactional
    public ApiResopnse<?> addCommentToTicket(@PathVariable String id, @RequestBody CommentDTO comment) {
//        the authenticated user must be the ticket owner or the ticket assigned to user
        TicketDTO ticketDTO = ticketService.getTicket(id);
        if (!ticketAuthService.isOwnerOrAssignedUser(ticketDTO)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to add a comment to this ticket", null);
        }

        TicketDTO ticket= ticketService.addCommentToTicket(id, comment);
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
        String username = principal.getName();

        Page<TicketDTO> tickets = null ;
        if (status == null) {
            tickets = ticketService.getTicketsByOwner(username, pageRequest);
        } else {
            tickets = ticketService.findTicketsByOwnerAndStatusWithoutComments(username, status, pageRequest);
        }
        return new ApiResopnse<>(HttpStatus.OK.value(), "Tickets retrieved successfully", tickets);
    }


    @PutMapping("/resolve/{id}")
    public ApiResopnse<TicketDTO> resolveTicket(@PathVariable String id, @RequestBody TicketResolutionDTO TicketResolution) {
//        you have to be the ticket assigned user to resolve the ticket
        TicketDTO ticketDTO = ticketService.getTicket(id);
        if (!ticketAuthService.isAssignedUser(ticketDTO)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to resolve this ticket", null);
        }
        TicketDTO ticket = ticketService.resolveTicket(id, TicketResolution);
        return new ApiResopnse<>(HttpStatus.OK.value(), "Ticket resolved successfully", ticket);
    }



    @PostMapping("/{id}/attachments")
    public ApiResopnse<?> uploadFile(@PathVariable String id, @RequestParam("file") MultipartFile file) {
//        you have to be the ticket owner or the ticket assigned user to upload a file
        TicketDTO ticketDTO = ticketService.getTicket(id);
        if (!ticketAuthService.isOwnerOrAssignedUser(ticketDTO)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to upload a file to this ticket", null);
        }

        // Maximum file size in bytes
        long maxFileSize = 10485760; // 10 MB

        if (file.getSize() > maxFileSize) {
            return new ApiResopnse<>(HttpStatus.BAD_REQUEST.value(), "File size exceeds the maximum limit of 10 MB", null);
        }

        try {
            TicketDTO ticket = ticketService.addFileToTicket(id, file);
            return new ApiResopnse<>(HttpStatus.OK.value(), "File uploaded successfully", ticket);
        } catch (IOException e) {
            // This will catch any IO exceptions, including file not found and permission issues
            System.out.println("Error occurred while uploading file: " + e.getMessage());
        } catch (Exception e) {
            // This will catch any other exceptions
            System.out.println("An error occurred: " + e.getMessage());
        }
        return new ApiResopnse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while uploading the file", null);
    }
    @GetMapping("/attachments/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String id) {
        // Load file from database
        AttachmentDTO attachment = ticketService.getAttachment(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }


}