package com.YusufFakhreddin.ICDTicketingSystem.ticket;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, String> {

//    query to get a ticket with its comments
    @EntityGraph(attributePaths = {"comments"})
    Ticket findTicketById(String id);
//    query to get all tickets without its comments
    @Query("SELECT t FROM Ticket t")
    List<Ticket> findAllTicketsWithoutComments();

//    query to get tickets by owner
    Page<Ticket> findTicketsByOwner_Username(String username, Pageable pageable);

//    query to get tickets by owner and status without comments
    @Query("SELECT t FROM Ticket t WHERE t.owner.username = ?1 AND t.status = ?2")
    Page<Ticket> findTicketsByOwnerAndStatusWithoutComments(String username, TicketStatus status,Pageable pageable);


}
