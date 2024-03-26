package com.YusufFakhreddin.TicketingSystem.ticket.service;

import com.YusufFakhreddin.TicketingSystem.attachment.AttachmentMapper;
import com.YusufFakhreddin.TicketingSystem.comment.CommentDTO;
import com.YusufFakhreddin.TicketingSystem.comment.CommentMapper;
import com.YusufFakhreddin.TicketingSystem.errorHandling.CustomException;
import com.YusufFakhreddin.TicketingSystem.attachment.AttachmentDTO;
import com.YusufFakhreddin.TicketingSystem.config.FileStorageProperties;
import com.YusufFakhreddin.TicketingSystem.attachment.AttachmentRepo;
import com.YusufFakhreddin.TicketingSystem.comment.CommentRepo;
import com.YusufFakhreddin.TicketingSystem.ticket.Ticket;
import com.YusufFakhreddin.TicketingSystem.ticket.TicketMapper;
import com.YusufFakhreddin.TicketingSystem.ticket.TicketRepo;
import com.YusufFakhreddin.TicketingSystem.attachment.Attachment;
import com.YusufFakhreddin.TicketingSystem.comment.Comment;
import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;
import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketResolutionDTO;
import com.YusufFakhreddin.TicketingSystem.ticket.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private AttachmentRepo attachmentRepo;
    @Autowired
    private FileStorageProperties fileStorageProperties;
    @Autowired
    private final TicketMapper ticketMapper;
    @Autowired
    private final CommentMapper commentMapper;
    @Autowired
    private final AttachmentMapper AttachmentMapper;

    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO){
        Ticket ticket = ticketMapper.toTicket(ticketDTO);
        Ticket newTicket = ticketRepo.save(ticket);
        return ticketMapper.toTicketDTO(newTicket);
    }

    @Override
    public TicketDTO getTicket(String id){
        // check for id existence and throw custom error if not
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        return ticketMapper.toTicketDTO(ticket);

    }

    @Override
    public Page<TicketDTO> getAllTickets(Pageable pageable){
        Page<Ticket> tickets = ticketRepo.findAll(pageable);
        return tickets.map(ticket -> ticketMapper.toTicketDTO(ticket));

    }

    @Override
    public TicketDTO updateTicket(String id , TicketDTO ticketDTO){
        // check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        // Convert TicketDTO to Ticket entity
        Ticket ticket = ticketMapper.toTicket(ticketDTO);
        // append id to ticket object
        ticket.setId(id);
        Ticket updatedTicket = ticketRepo.save(ticket);
        // Convert updated Ticket back to TicketDTO
        TicketDTO updatedTicketDTO = ticketMapper.toTicketDTO(updatedTicket);
        return updatedTicketDTO;
    }

    @Override
    public void deleteTicket(String id){

// check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        ticketRepo.deleteById(id);
    }


    public TicketDTO addCommentToTicket(String ticketId, CommentDTO commentDTO) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + ticketId));
//        convert commentDTO to comment
        Comment comment = commentMapper.toComment(commentDTO);
        ticket.addComment(comment);
        commentRepo.save(comment);
        ticketRepo.save(ticket);
        return ticketMapper.toTicketDTO(ticket);
    }

    public Page<TicketDTO> getTicketsByOwner(String username,Pageable pageable){
        Page<Ticket> tickets= ticketRepo.findTicketsByOwner_Username(username, pageable);
        return tickets.map(ticket -> ticketMapper.toTicketDTO(ticket));

    }

    @Override
    public Page<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, TicketStatus status, Pageable pageable) {
        Page<Ticket> tickets=  ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status, pageable);
        return tickets.map(ticket -> ticketMapper.toTicketDTO(ticket));
    }


    public List<TicketDTO> getTicketsByOwnerAndStatus(String username, TicketStatus status, Pageable pageable){
        Page<Ticket> tickets= ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status, pageable);
        return tickets.stream()
                .map(ticket -> ticketMapper.toTicketDTO(ticket))
                .collect(Collectors.toList());
    }


    @Override
    public TicketDTO resolveTicket(String id, TicketResolutionDTO TicketResolution) {
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        ticket.setStatus(TicketResolution.getStatus());
        ticket.setResolution(TicketResolution.getResolution());
        Ticket updatedTicket = ticketRepo.save(ticket);
        return ticketMapper.toTicketDTO(updatedTicket);
    }

    @Override
    public TicketDTO save(TicketDTO ticket) {
        Ticket newTicket = ticketMapper.toTicket(ticket);
        Ticket savedTicket = ticketRepo.save(newTicket);
        return ticketMapper.toTicketDTO(savedTicket);
    }

    @Override
    public AttachmentDTO getAttachment(String id) {
        Attachment attachment = attachmentRepo.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Attachment not found with id: " + id, null));

        return AttachmentMapper.toAttachmentDTO(attachment);
    }


    @Override
    public TicketDTO addFileToTicket(String ticketId, MultipartFile file) throws IOException {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + ticketId));

        // Generate a unique ID for the file
        String uniqueId = UUID.randomUUID().toString();

        // Get the upload directory from application.properties
        String uploadDir = fileStorageProperties.getUploadDir();

        // Get the original file name and extension
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Save the file to the directory specified in application.properties with its extension
        Path path = Paths.get(uploadDir + uniqueId + extension);
        Files.copy(file.getInputStream(), path);

        Attachment attachment = new Attachment();
        attachment.setFileName(uniqueId + extension);
        attachment.setFileType(file.getContentType());
        // Save the unique ID instead of the file data
        attachment.setData(uniqueId.getBytes());

        attachmentRepo.save(attachment);
        ticket.getAttachments().add(attachment);

        ticketRepo.save(ticket);

        return ticketMapper.toTicketDTO(ticket);
    }

}
