package com.YusufFakhreddin.ICDTicketingSystem.dao;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, String> {

}
