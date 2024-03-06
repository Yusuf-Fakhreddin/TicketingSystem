package com.YusufFakhreddin.ICDTicketingSystem.dao;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
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
    List<Ticket> findTicketsByOwner_Username(String username);

//    query to get tickets by owner and status without comments
    @Query("SELECT t FROM Ticket t WHERE t.owner.username = ?1 AND t.status = ?2")
    List<Ticket> findTicketsByOwnerAndStatusWithoutComments(String username, String status);

//    query to get tickets by assigned team and status without comments
    @Query("SELECT t FROM Ticket t WHERE t.assigned_team = ?1 AND t.status = ?2")
    List<Ticket> findTicketsByTeamAndStatusWithoutComments(String teamName, String status);

//    query to get tickets by assigned team without comments
@Query("SELECT t FROM Ticket t WHERE t.assigned_team = ?1")
List<Ticket> findTicketsByTeamWithoutComments(String team);
}
