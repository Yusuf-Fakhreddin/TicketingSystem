package com.YusufFakhreddin.ICDTicketingSystem.ticket;

import com.YusufFakhreddin.ICDTicketingSystem.errorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.attachment.AttachmentDTO;
import com.YusufFakhreddin.ICDTicketingSystem.config.FileStorageProperties;
import com.YusufFakhreddin.ICDTicketingSystem.attachment.AttachmentRepo;
import com.YusufFakhreddin.ICDTicketingSystem.comment.CommentRepo;
import com.YusufFakhreddin.ICDTicketingSystem.utilities.*;
import com.YusufFakhreddin.ICDTicketingSystem.attachment.Attachment;
import com.YusufFakhreddin.ICDTicketingSystem.comment.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.dto.TicketResolutionDTO;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.enums.TicketStatus;
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
    private ModelMapperUtil modelMapperUtil;

    @Override
    public TicketDTO createTicket(Ticket ticket){
        Ticket newTicket = ticketRepo.save(ticket);
        return modelMapperUtil.mapObject(newTicket, TicketDTO.class);
    }

    @Override
    public TicketDTO getTicket(String id){
        // check for id existence and throw custom error if not
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }

        return modelMapperUtil.mapObject(ticket, TicketDTO.class);

    }

    @Override
    public Page<TicketDTO> getAllTickets(Pageable pageable){
        Page<Ticket> tickets = ticketRepo.findAll(pageable);
        return tickets.map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class));

    }

    @Override
    public TicketDTO updateTicket(String id , TicketDTO ticketDTO){
        // check for id existence and throw custom error if not
        if (ticketRepo.findById(id).orElse(null) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + id);
        }
        // Convert TicketDTO to Ticket entity
        Ticket ticket = modelMapperUtil.mapObject(ticketDTO, Ticket.class);
        // append id to ticket object
        ticket.setId(id);
        Ticket updatedTicket = ticketRepo.save(ticket);
        // Convert updated Ticket back to TicketDTO
        TicketDTO updatedTicketDTO = modelMapperUtil.mapObject(updatedTicket, TicketDTO.class);
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


    public TicketDTO addCommentToTicket(String ticketId, Comment comment) {
        Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(),"Ticket id not found - " + ticketId));
        ticket.addComment(comment);
        commentRepo.save(comment);
        ticketRepo.save(ticket);
        return modelMapperUtil.mapObject(ticket, TicketDTO.class);
    }

    public Page<TicketDTO> getTicketsByOwner(String username,Pageable pageable){
        Page<Ticket> tickets= ticketRepo.findTicketsByOwner_Username(username, pageable);
        return tickets.map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class));

    }

    @Override
    public Page<TicketDTO> findTicketsByOwnerAndStatusWithoutComments(String username, TicketStatus status, Pageable pageable) {
        Page<Ticket> tickets=  ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status, pageable);
        return tickets.map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class));
    }


    public List<TicketDTO> getTicketsByOwnerAndStatus(String username, TicketStatus status, Pageable pageable){
        Page<Ticket> tickets= ticketRepo.findTicketsByOwnerAndStatusWithoutComments(username, status, pageable);
        return tickets.stream()
                .map(ticket -> modelMapperUtil.mapObject(ticket, TicketDTO.class))
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
        return modelMapperUtil.mapObject(updatedTicket, TicketDTO.class);
    }

    @Override
    public TicketDTO save(TicketDTO ticket) {
        Ticket newTicket = modelMapperUtil.mapObject(ticket, Ticket.class);
        Ticket savedTicket = ticketRepo.save(newTicket);
        return modelMapperUtil.mapObject(savedTicket, TicketDTO.class);
    }

    @Override
    public AttachmentDTO getAttachment(String id) {
        Attachment attachment = attachmentRepo.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Attachment not found with id: " + id, null));

        return modelMapperUtil.mapObject(attachment, AttachmentDTO.class);
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

        return modelMapperUtil.mapObject(ticket, TicketDTO.class);
    }


}
